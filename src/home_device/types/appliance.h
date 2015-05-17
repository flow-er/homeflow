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

struct appliance {
	int id;

	enum conn_t ctype;
	long addr;

	int (*runCommand)(long, int, int *);

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