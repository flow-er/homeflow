#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include "types/flow.h"

void printNodeInfoForTest(struct node *node);
void runNode(struct node *node);
void run(struct flow *flow);

void *input(void *arg);

int flag = 1;
int go = 0;

int main(int argc, const char *argv[]) {
	struct flow *flow;
	char path[BUFSIZ];

	if (argc < 2) return 0;

	sprintf(path, "./user/flows/%s.xml", argv[1]);

	flow = parseFlow(path);
	run(flow);

	freeFlow(flow);
	flag = 0;
	return 0;
}

void run(struct flow *flow) {
	struct node *curr = flow->head;

	if (flow->isAuto == TRUE) {
		//trigger

		curr = curr->next;
	}

	runNode(curr);
}

void runNode(struct node *node) {
	printNodeInfoForTest(node);

	printf("press any button to continue.\n");
	getchar();

	switch (node->type) {
		case T_ACTION:
			//get char ->bluetooth
		case T_NOTIFY:
			//bluetooth
		case T_CONDITION:
			//bluetooth
			//goto child
		case T_LOOP:
			//bluetooth
			//loop
			//goto child
		case T_COWORK:
			//goto child
			;
	}

	//notify to user (msg queue)

	if (node->child) runNode(node->child);
	if (node->next) runNode(node->next);
}

void printNodeInfoForTest(struct node *node) {
	printf("NODE INFO\n");

	switch (node->type) {
		case T_CONDITION:
		case T_LOOP:
			printf("condition : %s %s\n", cTypeName[node->cond], node->value);

		case T_ACTION:
		case T_NOTIFY:
			printf("id        : %s\n", node->appid);
			printf("command   : %s\n", node->command);

		default:
			printf("type      : %s\n", nTypeName[node->type]);
			break;
	}
}
