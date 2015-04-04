#include <sys/types.h>
#include "flow.h"

#define PATH_CD		"./"
#define PATH_USER	"user/"
#define PATH_FLOW	"flows/"

struct event {
	struct flow *flow;
	pid_t pid;

	struct event *next;
};

struct scheduler {
	struct event *head;
};

void scheduleEvents(struct scheduler *scheduler);
