#include <stdio.h>
#include <errno.h>
#include <time.h>
#include <signal.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>

#include "types/scheduler.h"

int executeFlow();
void signalHandler(int);

struct scheduler scheduler;
int completed = 0;

int main(int argc, const char *argv[]) {
	int status;

	signal(SIGUSR1, signalHandler);
	scheduleEvents(&scheduler, INIT);

	//while(1) {
	if (executeFlow() == 1) {
		printf("flow_manger : failed to execute \'flow_executer\'\n");
		return 0;
	}
	completed = waitpid(-1, &status, 0);
	//
	//conflict, when two or more child can be terminated simultaneously.

	//solution (put it in function'executeFlow()')
	//struct stat sts;
	//notice : can't do test on OSX.
	//pseudo : loop for all scheduled events.
	//if(stat("/proc/<pid>", &sts) == -1 && errno == ENOENT) {
	//	//means : process doesn't exist.
	//};
	//}

	return 0;
}

int executeFlow() {
	struct event *event = NULL;

	for (event = scheduler.head; event != NULL; event = event->next) {
		char *argv[3];

		if (completed && completed == event->pid) {
			event->pid = 0;
		}
		if (event->pid != 0) continue;

		argv[0] = "flow_executer";
		argv[1] = (char *) malloc(sizeof(char) * BUFSIZ);
		argv[2] = NULL;

		sprintf(argv[1], "%d", event->flow->id);

		if ((event->pid = fork())) {
			printf("child : %d\n", event->pid);
		} else {
			if (execv("./flow_executer", argv) == -1) {
				return 1;
			}
		}

		free(argv[1]);
	}

	return 0;
}

void signalHandler(int signal) {
	if (signal == SIGUSR1) {
		printf("flow_manager : Do re-schedule!\n");
		scheduleEvents(&scheduler, REDO);
	} else {
		struct event *event = NULL;

		for (event = scheduler.tail; event != NULL; event = event->prev) {
			freeFlow(event->flow);
			free(event);
		}
	}
}
