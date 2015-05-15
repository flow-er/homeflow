#include <stdio.h>
#include <time.h>

#include <libxml/xmlmemory.h>
#include <libxml/parser.h>

#define NT 5

enum node_t {
	T_ACTION = 0,
	T_CONDITION,
	T_LOOP,
	T_COWORK,
	T_TRIGGER
};

enum cond_t {
	EQUAL = 0,
	LESS,
	LESS_OR_EQUAL,
	MORE,
	MORE_OR_EQUAL
};

struct node {
	enum node_t type;

	int appid;
	uint command, option;

	struct node *next;
	struct node *child;
};

struct flow {
	int id;
	int isAuto;

	struct node *head;
};

struct flow *parseFlow(const char *path);
void freeFlow(struct flow *flow);
