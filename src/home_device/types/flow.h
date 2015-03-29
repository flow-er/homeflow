#include <stdio.h>
#include <time.h>

#include <libxml/xmlmemory.h>
#include <libxml/parser.h>

#if __STDC_VERSION__ >= 199901L
	#include <stdbool.h>
#else
	#define bool int;
	#define true 1
	#define false 0
#endif

//types of node
enum nType {DOACT = 0, NOTIFY, CONDITION, LOOP, TRIGGER};
extern const char *nTypeName[5];

//types of condition
enum cType {EQUAL = 0, LESS, LESS_OR_EQUAL, MORE, MORE_OR_EQUAL};

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
	struct tm *modi;
	
	int id;
	char *name;
	char *description;
	bool isAuto;
	
	struct node *head;
};

void parseFlow(struct flow *flow, const char *path, struct tm *time);
void printFlow(struct flow *flow);