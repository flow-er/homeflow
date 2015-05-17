#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "types/flow.h"
#include "types/appliance.h"
#include "types/msg.h"

#include "va/counter.h"
#include "va/timer.h"

void runNode(struct node *node);
void run(struct flow *flow);

const char *procname = "flow_executer";

struct message msg;
int msg_id;

struct appl_list *apps;

int main(int argc, const char *argv[]) {
	struct flow *flow;
	char path[BUFSIZ];

	if (argc < 2) return 0;

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
	runNode(flow->head);

	msg.state = FLOW_COMPLETED;
	msgsnd(msg_id, &msg, MSGSIZE, 0);
}

void runNode(struct node *node) {
	struct appliance *app = apps->head;
	int ret = 0;

	// Find the appliance to send command.
	while (app) {
		if (app->id == node->appid) break;
		app = app->next;
	}

	// Send command to the appliance and ...
	if (app->runCommand(app->addr, node->command, &ret) == -1) {
		// If failed to send, notify to 'flow_manager' and exit.
		msg.state = FLOW_FAILED;
		msgsnd(msg_id, &msg, MSGSIZE, 0);

		exit(0);
	}

	switch (node->type) {
		case N_CONDITION:
			// CONTENTS :
			// if the condition is accepted, run child nodes
			// else, skip child nodes.
			break;

		case N_LOOP:
			// CONTENTS :
			// if the condition is accepted, run child nodes
			//                               and do it again.
			// else, skip child nodes.
			break;

		case N_COWORK:
			// CONTENTS :
			// Make threads and when all of them done,
			// go to next.
			break;

		case N_TRIGGER:
			// CONTENTS :
			// if the condition is accepted, break.
			// else, do it again.
			break;

		default:
			break;
	}

	msg.state = FLOW_DONENODE;
	msgsnd(msg_id, &msg, MSGSIZE, 0);

	if (node->child) runNode(node->child);
	if (node->next) runNode(node->next);
}
