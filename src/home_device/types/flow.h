#include <stdio.h>
#include <time.h>

#include <libxml/xmlmemory.h>
#include <libxml/parser.h>

#define NODE_TN 5
#define COND_TN 5

enum node_t {
	T_ACTION = 0,
	T_CONDITION,
	T_LOOP,
	T_COWORK,
	T_TRIGGER
};

extern const char *nodeTypes[NODE_TN];

enum cond_t {
	EQUAL = 0,
	LESS,
	LESS_OR_EQUAL,
	MORE,
	MORE_OR_EQUAL
};

extern const char *condTypes[COND_TN];

struct node {
	enum node_t type;

	int appid;
	int command;
	int notify;

	struct node *next;

	enum cond_t cond;
	char *value;

	struct node *child;
};

struct flow {
	int id;
	char *name;
	char *description;
	int isAuto;

	struct node *head;
};

struct flow *parseFlow(const char *path);
void freeFlow(struct flow *flow);
