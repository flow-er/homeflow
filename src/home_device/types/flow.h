#include <stdio.h>
#include <time.h>

#include <libxml/xmlmemory.h>
#include <libxml/parser.h>

#define TRUE 1
#define FALSE 0

//types of node
enum nType {
	T_ACTION = 0,
	T_NOTIFY,
	T_CONDITION,
	T_LOOP,
	T_COWORK,
	T_TRIGGER
};
extern const char *nTypeName[6];

//types of condition
enum cType {
	EQUAL = 0,
	LESS,
	LESS_OR_EQUAL,
	MORE,
	MORE_OR_EQUAL
};

struct node {
	enum nType type;

	char *appid;
	char *command;

	struct node *next;

	enum cType cond;
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
void printFlow(struct flow *flow);
