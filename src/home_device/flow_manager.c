#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>
#include <time.h>
#include <signal.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/types.h>

#include "types/scheduler.h"
#include "types/msg.h"

#define MAXLINE 127

#define SERVER_ADDR "52.68.106.249"
#define SERVER_PORT 80

#define RD 0
#define WR 1

typedef int fd;

void executeMsgManager(int *fd);
void initServerSocket(int *fd);
void executeFlows();
void signalHandler(int);

const char *procname = "flow_manager";

struct scheduler scheduler;
struct message msg;

int main(int argc, const char *argv[]) {
	fd server[2], pipe[2];
	fd_set fds;

	int fd_max;

	signal(SIGINT, signalHandler);

	scheduleEvents(&scheduler, INIT);
	executeMsgManager(pipe);
	initServerSocket(server);

	FD_ZERO(&fds);

	FD_SET(pipe[RD], &fds);
	FD_SET(pipe[WR], &fds);
	FD_SET(server[RD], &fds);

	fd_max = pipe[WR];
	
	while (1) {
		fd_set temp = fds;
		int i;

		executeFlows();

		if (select(fd_max + 1, &temp, 0, 0, 0) < 0) {
			printf("%s : Failed to select.\n", procname);
			exit(1);
		}

		if (FD_ISSET(pipe[RD], &temp)) {
			struct event *event;
			const int ok = 1;
			
			read(pipe[RD], &msg, sizeof(struct message));
			write(pipe[WR], &ok, sizeof(int));
			
			for (event = scheduler.head; event != NULL; event = event->next) {
				if (msg.pid == event->pid) {
					event->pid = 0;
					msg.pid = event->flow->id;
					
					break;
				}
			}
			
			write(server[WR], &msg, sizeof(struct message));
		} else if (FD_ISSET(server[RD], &temp)) {
			// TODO : Write code.
		} else if (FD_ISSET(server[WR], &temp)) {
			// TODO : Write code.
			scheduleEvents(&scheduler, REDO);
		}
	}

	return 0;
}

void executeMsgManager(int *fd) {
	int fd1[2], fd2[2];
	int pid;
	char *argv[4];

	pipe(fd1);
	pipe(fd2);

	argv[0] = "msg_manager";
	argv[1] = (char *) malloc(sizeof(char) * 5);
	argv[2] = (char *) malloc(sizeof(char) * 5);
	argv[3] = NULL;

	sprintf(argv[1], "%d", fd1[1]);
	sprintf(argv[2], "%d", fd2[0]);

	if ((pid = fork())) {
		close(fd1[1]);
		close(fd2[0]);
	} else {
		close(fd1[0]);
		close(fd2[1]);

		if (execv("./msg_manager", argv) == -1) {
			printf("%s : Failed to execute msg_manager", procname);
			exit(1);
		}
	}

	fd[RD] = fd1[0];
	fd[WR] = fd2[1];

	free(argv[1]);
}

void initServerSocket(int *fd) {
	struct sockaddr_in addr;
	
	if ((fd[WR] = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
		printf("%s : Failed to create socket.\n", procname);
		exit(1);
	}
	
	memset(&addr, 0, sizeof(struct sockaddr_in));
	addr.sin_family = AF_INET;
	addr.sin_addr.s_addr = inet_addr(SERVER_ADDR);
	addr.sin_port = htons(SERVER_PORT);

	if (connect(fd[WR], (struct sockaddr *) &addr, sizeof(addr)) == -1) {
		printf("%s : Failed to connect with server.\n", procname);
		exit(1);
	}

	//send to alert connection success with some kind of id
	//do something for fd[RD] (in fact, server[RD]) or do else.
	//
	//if ((fd[RD] = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
	//	printf("%s : Failed to create socket.\n", procname);
	//	exit(1);
	//}
	//
	//memset(&addr, 0, sizeof(struct sockaddr_in));
	//addr.sin_family = AF_INET;
	//addr.sin_addr.s_addr = htonl(INADDR_ANY);
	//addr.sin_port = htons(19916);
	//
	//if (bind(fd[RD], (const struct sockaddr *) &addr, sizeof(addr)) < 0) {
	//	printf("%s : Failed to bind socket.\n", procname);
	//	exit(1);
	//}
	//
	//listen(fd[RD], 5);
}

void executeFlows() {
	struct event *event = NULL;

	for (event = scheduler.head; event != NULL; event = event->next) {
		char *argv[3];

		if (event->pid != 0) continue;
		if (!event->flow->isAuto) continue;

		argv[0] = "flow_executer";
		argv[1] = (char *) malloc(sizeof(char) * BUFSIZ);
		argv[2] = NULL;

		sprintf(argv[1], "%d", event->flow->id);

		if (!(event->pid = fork())) {
			if (execv("./flow_executer", argv) == -1) {
				printf("%s : Failed to execute flow_executer", procname);
				exit(1);
			}
		}

		free(argv[1]);
	}
}

void signalHandler(int signal) {
	struct event *event = NULL;

	for (event = scheduler.tail; event != NULL; event = event->prev) {
		freeFlow(event->flow);
		free(event);
	}

	exit(0);
}
