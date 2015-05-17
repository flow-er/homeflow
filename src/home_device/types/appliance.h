#define APPL_PATH "./user/appliances.xml"

#define SYS_TIMER 0
#define SYS_COUNTER 1

enum virtual_devices {
	V_COUNTER = 0,
	V_TIMER
};

enum conn_t {
	C_VIRTUAL = 0,
	C_BLUE
};

enum opt_t {
	O_NOTIFY = 0,
	O_NOWAIT,
	O_WAIT
};

enum cond_t {
	EQUAL = 0,
	LESS,
	LESS_OR_EQUAL,
	MORE,
	MORE_OR_EQUAL
};

typedef struct cmdSet {
	enum opt_t type;
	
	int command;
	int option;
} cmdset;

struct appliance {
	int id;

	enum conn_t ctype;
	long addr;

	int (*runCommand)(long, cmdset, int *);

	struct appliance *prev;
	struct appliance *next;
};

struct appl_list {
	struct appliance *head;
	struct appliance *tail;
};

struct appl_list *parseApplList(const char *path);
void freeApplList(struct appl_list *apps);

int runCommand()