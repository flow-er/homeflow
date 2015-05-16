#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include "types/flow.h"
#include "types/msg.h"

#include "va/counter.h"
#include "va/timer.h"

void printNodeInfoForTest(struct node *node);
void runNode(struct node *node);
void run(struct flow *flow);

const char *procname = "flow_executer";

struct message msg;
int id;

int main(int argc, const char *argv[]) {
	struct flow *flow;
	char path[BUFSIZ];

	if (argc < 2) return 0;

	// BUG : Clearly have no effects at all.
	if ((id = msgget(MKEY, 0)) < 0) {
		printf("%s : can't get message queue.\n", procname);
		return 0;
	}

	msg.pid = getpid();

	sprintf(path, "./user/flows/%s.xml", argv[1]);

	flow = parseFlow(path);
	run(flow);

	freeFlow(flow);
	return 0;
}

void run(struct flow *flow) {
	struct node *curr = flow->head;

	if (flow->isAuto) {
		//trigger

		curr = curr->next;
	}

	runNode(curr);
}

void runNode(struct node *node) {
	printNodeInfoForTest(node);

	//send command to appliance via bluetooth. (except SYS_TIMER, SYS_COUNTER)
	//receive command executed.
	//temporary
	printf("press any button to continue.\n");
	getchar();

	switch (node->type) {
		case T_CONDITION:
			//bluetooth
			//goto child
		case T_LOOP:
			//bluetooth
			//loop
			//goto child
		case T_COWORK:
			//goto child
		default:
			break;
	}

	//receive command done.
	//notify to user via msg queue & synchronizer.
	msg.state = FLOW_DONENODE;
	int j = msgsnd(id, &msg, MSGSIZE, 0);

	if (node->child) runNode(node->child);
	if (node->next) runNode(node->next);
}

void printNodeInfoForTest(struct node *node) {
	printf("NODE INFO\n");

	// TODO : Write code.
}
