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

void sendMessage(int node, enum state state);
void signalHandler(int signal);

struct cmdOption {
	enum cond_t cond;
	int val;
};

const char *procname = "flow_executer";

struct appl_list *apps;
struct message msg;
int msg_id;

int usr_exec;

int main(int argc, const char *argv[]) {
	struct flow *flow;
	char path[BUFSIZ];

	if (argc < 3) return 0;
	usr_exec = atoi(argv[2]);

	printf("%s : flow %s is executed ", procname, argv[1]);
	printf("by %s.\n", (usr_exec ? "user" : "scheduler"));

	signal(SIGUSR1, signalHandler);

	// BUG : Clearly have no effects at all.
	if ((msg_id = msgget(MSG_KEY, 0)) < 0) {
		printf("%s : can't get message queue.\n", procname);
		return 0;
	}

	sprintf(path, "%s%s.xml", FLOW_DIR, argv[1]);

	apps = parseApplList(APPL_PATH);

	flow = parseFlow(path);

	msg.type = FROM_FLOW_EXECUTER;
	msg.id = flow->id;

	run(flow);

	freeFlow(flow);
	return 0;
}

void run(struct flow *flow) {
	if (usr_exec) sendMessage(0, FLOW_START);

	runNode(flow->head);

	printf("%s : flow %d is successfully completed.\n", procname, flow->id);
	sendMessage(0, FLOW_DONE);
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
			sleep(1000);

			if (ret == -1) sendMessage(0, FLOW_FAILED);
			sendMessage(node->num, NODE_COMPLETED);
			break;

		case N_TRIGGER:
			if (usr_exec) break;

			set.type = O_WAIT;

			while (ret == 0) {
				ret = app->runCommand(app->addr, set);

				if (ret == -1) sendMessage(0, FLOW_FAILED);
				if (ret) sendMessage(0, FLOW_START);
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

	for (temp = head; temp != NULL; temp = temp->next) {
		++num;
	}

	thread = (pthread_t *) malloc(sizeof(pthread_t) * num);

	for (i = 0, temp = head; i < num; i++) {
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

void sendMessage(int node, enum state state) {
	msg.state = state;
	msg.node = node;
	msgsnd(msg_id, &msg, MSGSIZE, 0);

	if (state == FLOW_FAILED) exit(0);
}

void signalHandler(int signal) {
	freeApplList(apps);
	exit(0);
}
