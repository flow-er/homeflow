#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <unistd.h>
#include <arpa/inet.h>

#include <errno.h>

#include "types/scheduler.h"
#include "types/msg.h"

#define RD 0
#define WR 1

#define SERVER_ADDR "52.68.106.249"
#define SERVER_PORT 52223

typedef int fd;

pid_t executeMsgManager(int *fd);
pid_t executeAppManager();
void executeFlows(int exec, int del);

void signalHandler(int);

const char *procname = "flow_manager";

struct scheduler scheduler;

pid_t pid_msgman, pid_appman;

int main(int argc, const char *argv[]) {
	fd server, pipe[2];
	fd_set fds;
	int fd_max;
	struct sockaddr_in addr;

	int usr_exec = -1, deletion = -1;

	signal(SIGUSR1, signalHandler);

	scheduleEvents(&scheduler, INIT);

	pid_msgman = executeMsgManager(pipe);
	//	pid_appman = executeAppManager();

	// Initialize server socket.
	server = socket(PF_INET, SOCK_STREAM, 0);

	memset(&addr, 0, sizeof(struct sockaddr_in));
	addr.sin_family = AF_INET;
	addr.sin_addr.s_addr = inet_addr(SERVER_ADDR);
	addr.sin_port = htons(SERVER_PORT);

	if (connect(server, (struct sockaddr *) &addr, sizeof(addr)) == -1) {
		printf("%s : Failed to connect with server.\n", procname);
		exit(1);
	}

	while (1) {
		fd_set temp;

		executeFlows(usr_exec, deletion);
		usr_exec = -1;

		set_fds:

		FD_ZERO(&fds);

		FD_SET(server, &fds);
		FD_SET(pipe[RD], &fds);
		FD_SET(pipe[WR], &fds);

		fd_max = pipe[WR];

		temp = fds;

		if (select(fd_max + 1, &temp, 0, 0, 0) < 0) {
			if (errno == EINTR) goto set_fds;
			printf("%s : Failed to select.\n", procname);
			exit(1);
		}

		if (FD_ISSET(pipe[RD], &temp)) {
			static const char *status[5] = { "FLOW_START", "FLOW_DONE",
					"FLOW_FAILED", "NODE_RUNNING", "NODE_COMPLETED" };

			struct message msg;
			char message[BUFSIZ];
			const int ok = 1;

			read(pipe[RD], &msg, sizeof(struct message));

			if (msg.type == FROM_FLOW_EXECUTER) {
				struct event *event = scheduler.head;

				while (event && msg.id != event->flow->id) {
					event = event->next;
				}

				if (msg.state == FLOW_DONE || msg.state == FLOW_FAILED) {
					event->pid = 0;
				}

				sprintf(message, "FLOW_STATUS|%d|%d|%s", msg.id, msg.node,
						status[msg.state - 1]);
			} else {
				sprintf(message, "NEW_DEVICE|%d", msg.id);
			}

			ssize_t a = write(server, message, strlen(message) + 1);

			printf("%s : Message is sent to server.\n", procname);
			printf("message : %ld, %s\n", a, message);

			write(pipe[WR], &ok, sizeof(int));
		}
		if (FD_ISSET(server, &temp)) {
			char buf[BUFSIZ];
			long len;

			char path[BUFSIZ] = "";
			int file = 0;

			while ((len = recv(server, buf, BUFSIZ - 1, MSG_DONTWAIT)) > -1) {
				char *data = strpbrk(buf, "|");

				*data = '\0';
				++data;

				if (!strcmp(buf, "START")) {
					usr_exec = atoi(data);
					printf("%s : User executed flow %d.\n", procname, usr_exec);

				} else if (!strcmp(buf, "DELETE")) {
					deletion = atoi(data);
					printf("%s : User erased flow %d.\n", procname, deletion);
				} else if (!strcmp(buf, "FLOW")) {
					char *xml = strpbrk(data, "|");

					*xml = '\0';
					++xml;

					sprintf(path, "%s%s", TEMP_DIR, data);

					file = open(path, O_WRONLY | O_CREAT | O_TRUNC, 0644);
					printf("%s : Receiving new file \'%s\'...\n", procname,
							data);
					write(file, xml, len);
				} else {
					write(file, buf, len);
				}
			}

			printf("%s : Receiving done.\n", procname);
			scheduleEvents(&scheduler, REDO);
			if (file) close(file);
		}
	}

	return 0;
}

pid_t executeMsgManager(int *fd) {
	pid_t pid;

	int fd1[2], fd2[2];
	char *argv[4];

	pipe(fd1);
	pipe(fd2);

	argv[0] = "msg_manager";
	argv[1] = (char *) malloc(sizeof(char) * 3);
	argv[2] = (char *) malloc(sizeof(char) * 3);
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
			printf("%s : Failed to execute msg_manager\n", procname);
			exit(1);
		}
	}

	fd[RD] = fd1[0];
	fd[WR] = fd2[1];

	free(argv[1]);

	return pid;
}

pid_t executeAppManager() {
	pid_t pid;
	char *argv[2];

	argv[0] = "app_manager";
	argv[1] = NULL;

	pid = fork();

	if (!pid) {
		if (execv("./app_manager", argv) == -1) {
			printf("%s : Failed to execute app_manager\n", procname);
			exit(1);
		}
	}

	return pid;
}

void executeFlows(int exec, int del) {
	struct event *event = NULL;

	for (event = scheduler.head; event != NULL; event = event->next) {
		char *argv[4];

		if (event->flow->id == del) {
			if (event->prev)
				event->prev->next = event->next;
			else
				scheduler.head = event->next;

			if (event->next)
				event->next->prev = event->prev;
			else
				scheduler.tail = event->prev;

			freeFlow(event->flow);
			free(event);

			continue;
		}

		if (event->pid != 0) continue;
		if ((event->flow->id != exec) && (!event->flow->isAuto)) continue;

		argv[0] = "flow_executer";
		argv[1] = (char *) malloc(sizeof(char) * BUFSIZ);
		argv[2] = (char *) malloc(sizeof(char) * 2);
		argv[3] = NULL;

		sprintf(argv[1], "%d", event->flow->id);
		sprintf(argv[2], "%d", (event->flow->id == exec ? 1 : 0));

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

	kill(pid_msgman, SIGUSR1);
	kill(pid_appman, SIGUSR1);

	for (event = scheduler.head; event != NULL; event = event->next) {
		if (!event->pid) continue;
		kill(event->pid, SIGUSR1);
	}

	freeScheduler(&scheduler);
	exit(0);
}
