#include <sys/types.h>

#define FLOW_DIR "./user/flows/"
#define TEMP_DIR "./user/temp/"

#define NT 5

extern const char *types[NT];

enum node_t {
	N_ACTION = 0,
	N_CONDITION,
	N_LOOP,
	N_COWORK,
	N_TRIGGER
};

struct node {
	enum node_t type;

	int appid;
	uint command, option;

	int num;

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
