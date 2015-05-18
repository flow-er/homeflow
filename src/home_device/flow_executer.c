#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <unistd.h>

#include "types/flow.h"
#include "types/appliance.h"
#include "types/msg.h"

void runNode(struct node *node);
void run(struct flow *flow);

void runNodesAtOnce(struct node *head);
void *runNodeByThread(void *temp);

inline void sendMessage(int node, enum state state);
void signalHandler(int signal);

struct cmdOption {
	enum cond_t cond;
	int val;
};

const char *procname = "flow_executer";

struct appl_list *apps;
struct message msg;
int msg_id;

int main(int argc, const char *argv[]) {
	struct flow *flow;
	char path[BUFSIZ];

	if (argc < 2) return 0;

	signal(SIGUSR1, signalHandler);

	// BUG : Clearly have no effects at all.
	if ((msg_id = msgget(MSG_KEY, 0)) < 0) {
		printf("%s : can't get message queue.\n", procname);
		return 0;
	}

	msg.pid = getpid();

	sprintf(path, "./user/flows/%s.xml", argv[1]);

	apps = parseApplList(APPL_PATH);

	flow = parseFlow(path);
	run(flow);

	freeFlow(flow);
	return 0;
}

void run(struct flow *flow) {
	if (!flow->isAuto) sendMessage(0, FLOW_RUNNING);

	runNode(flow->head);

	sendMessage(0, FLOW_COMPLETED);
}

void runNode(struct node *node) {
	struct appliance *app = apps->head;

	// int ret : Get return value for command.
	//
	//           value -1 : Failed to connect.
	//                  0 : Comparison expression is wrong.
	//                  1 : Comparison expression is right.
	int ret = -1;

	cmdset set;

	// Find the appliance to send command.
	while (app) {
		if (app->id == node->appid) break;
		app = app->next;
	}

	set.command = node->command;
	set.option = node->option;

	switch (node->type) {
		case N_ACTION:
			set.type = O_NOTIFY;

			sendMessage(node->num, NODE_RUNNING);
			ret = app->runCommand(app->addr, set);

			if (ret == -1) sendMessage(0, FLOW_FAILED);
			sendMessage(node->num, NODE_COMPLETED);
			break;

		case N_TRIGGER:
			set.type = O_WAIT;

			while (ret == 0) {
				ret = app->runCommand(app->addr, set);

				if (ret == -1) sendMessage(0, FLOW_FAILED);
				if (ret) sendMessage(0, FLOW_RUNNING);
			}
			break;

		case N_CONDITION:
			set.type = O_NOWAIT;

			ret = app->runCommand(app->addr, set);

			if (ret == -1) sendMessage(0, FLOW_FAILED);
			if (ret && node->child) runNode(node->child);
			break;

		case N_LOOP:
			set.type = O_NOWAIT;

			while (ret != 0) {
				ret = app->runCommand(app->addr, set);

				if (ret == -1) sendMessage(0, FLOW_FAILED);
				if (ret && node->child) runNode(node->child);
			}
			break;

		case N_COWORK:
			runNodesAtOnce(node->child);
			break;
	}

	if (node->next) runNode(node->next);
}

void runNodesAtOnce(struct node *head) {
	struct node *temp;
	int num = 0;
	int i;

	pthread_t *thread;

	for (temp = head; temp != NULL; temp = temp->next)
		++num;
	temp = head;

	thread = (pthread_t *) malloc(sizeof(pthread_t) * num);

	for (i = 0; i < num; i++) {
		pthread_create(&thread[i], NULL, runNodeByThread, temp);
		temp = temp->next;
	}

	for (i = 0; i < num; i++) {
		pthread_join(thread[i], NULL);
	}
}

void *runNodeByThread(void *node) {
	runNode((struct node *) node);
	return NULL;
}

inline void sendMessage(int node, enum state state) {
	msg.state = state;
	msg.node = node;
	msgsnd(msg_id, &msg, MSGSIZE, 0);

	if (state == FLOW_FAILED) exit(0);
}

void signalHandler(int signal) {
	freeApplList(apps);
	exit(0);
}
