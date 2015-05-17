#include <sys/types.h>

#include "flow.h"

#define INIT 0
#define REDO 1

struct event {
	struct flow *flow;
	pid_t pid;

	struct event *prev;
	struct event *next;
};

struct scheduler {
	struct event *head;
	struct event *tail;
};

void scheduleEvents(struct scheduler *scheduler, int mode);
void freeScheduler(struct scheduler *scheduler);
