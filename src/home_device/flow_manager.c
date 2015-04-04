#include <stdio.h>
#include <errno.h>
#include <time.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>

#include "types/scheduler.h"

void Signal_INT_Handler(int);

struct scheduler scheduler;

int main(int argc, const char *argv[]) {
	struct event *event = NULL;
	int status;

	signal(SIGUSR1, Signal_INT_Handler);
	scheduleEvents(&scheduler);

	for (event = scheduler.head; event != NULL; event = event->next) {
		char *argv[1];
		argv[0] = (char *) malloc(sizeof(char) * BUFSIZ);

		sprintf(argv[0], "%d", event->flow->id);

		if ((event->pid = fork())) {
			printf("child : %d\n", event->pid);
		} else {
			if (execvp("flow_executer", argv) == -1) {
				printf("ERROR!!\n");

				//exception for no source file.
				if (errno == ENOENT)
					printf("case 1\n");
				//exception for permission denied.
				else if (errno == EACCES)
					printf("case 2\n");
				//exception for no directory.
				else if (errno == ENOTDIR)
					printf("case 3\n");
				return 0;
			}
		}
	}

	waitpid(-1, &status, 0);
	printf("LAST\n");
	return 0;
}

/**
 * @classname Signal_INT_Handler
 * @brief refreshing xml files
 * @details compare old file's information with new one and refresh new file's information (flows)
 * @param (signal)
 * @author Choi won beom, Kim hoyunjigi
 * @version 0.1
 */
void Signal_INT_Handler(int num) {
	_finddatai64_t c_file;
	intptr_t hFile;
	struct tm *t;
	char path[] = "*.*"; // flow directory

	if ( (hFile = _findfirsti64(path, &c_file)) == -1L ) {
		switch (errno) {
		case ENOENT:
			printf(":: No files in this directory ::\n"); break;
		case EINVAL:
			fprintf(stderr, "Invalid path name.\n"); exit(1); break;
		case ENOMEM:
			fprintf(stderr, "Not enough memory or file name too long.\n"); exit(1); break;
		default:
			fprintf(stderr, "Unknown error.\n"); exit(1); break;
		}
	} // end if
	else {
		do {
			t = localtime(&c_file.time_write);

			if((t->tm_sec != tm->tm_sec && t->tm_min != tm->tm_min && t->tm_hour!= tm->tm_hour))   
			{
				flow = (struct flow *) malloc(sizeof(struct flow));
				parseFlow(flow, const char *path, t);
			}

		} while (_findnexti64(hFile, &c_file) == 0);
	} // end else
	_findclose(hFile);
}

