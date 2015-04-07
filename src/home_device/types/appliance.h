#define SYS_TIMER 0
#define SYS_COUNTER 1

struct appliance {
	int id;
	char *name;
	char *description;

	int *command;
};
