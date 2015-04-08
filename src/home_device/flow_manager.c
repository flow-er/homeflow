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

#define RD 0
#define WR 1

typedef int fd;

void executeMsgManager(int *fd);
void initServerSocket(int *fd);
void executeFlow();
void signalHandler(int);

const char *proc = "flow_manager";

struct scheduler scheduler;
struct message msg;

int main(int argc, const char *argv[]) {
	fd socket[2], pipe[2];
	fd_set fds;
	
	///////////////////////////////////////////////////////////////////////////
	struct sockaddr_in clnt_addr;
	socklen_t addrlen = sizeof(clnt_addr);
	
	int fd_max;
	int filesize = 0;
	int fp;
	int total, sread = 0;
	char buf[MAXLINE + 1];
	
	int flag = 0;
	char flowpath[40] = "./user/temp/flows/";
	char applpath[40] = "./user/temp/appliances/";

	char cli_ip[20], filename[20], path[60];
	///////////////////////////////////////////////////////////////////////////
	
	signal(SIGINT, signalHandler);
	signal(SIGKILL, signalHandler);

	scheduleEvents(&scheduler, INIT);
	executeMsgManager(pipe);
	initServerSocket(socket);
	
	FD_ZERO(&fds);
	
	FD_SET(pipe[RD], &fds);
	FD_SET(pipe[WR], &fds);
	FD_SET(socket[RD], &fds);
	
	fd_max = pipe[WR];

	while (1) {
		fd_set temp = fds;
		int i;
		
		executeFlow();

		if (select(fd_max+1, &temp, 0, 0, 0) < 0) {
			printf("%s : Failed to select.\n", proc);
			exit(1);
		}

		for (i = 0; i < fd_max+1; i++) {
			if (FD_ISSET(i, &temp)) {
				if (i == socket[RD]) {
					socket[WR] = accept(socket[RD],
											(struct sockaddr *) &clnt_addr,
											&addrlen);

					if (socket[WR] < 0) {
						perror("accept fail");
						exit(0);
					}

					FD_SET(socket[WR], &fds);
					if (fd_max < socket[WR]) fd_max = socket[WR];
					
					printf("Connection : file descripter %d \n", socket[WR]);
					inet_ntop(AF_INET, &clnt_addr.sin_addr.s_addr, cli_ip,
								sizeof(cli_ip));
					printf("IP : %s ", cli_ip);
					printf("Port : %x \n", ntohs(clnt_addr.sin_port));
				} else if (i == pipe[RD]) {
					struct event *event;
					int ok = 1;
					
					read(pipe[RD], &msg, sizeof(struct message));
					write(pipe[WR], &ok, sizeof(int));
					
					for (event = scheduler.head; event != NULL; event = event->next) {
						if(msg.pid == event->pid) {
							event->pid = 0;
							msg.pid = event->flow->id;
						}
					}
					
					write(socket[WR], &msg, sizeof(struct message));
				} else if(i == socket[WR]) {
					memset(filename, 0, 20);
					memset(path, 0, 60);
					
					recv(socket[WR], filename, sizeof(filename), 0);
					printf("%s", filename);
					
					read(socket[WR], &filesize, sizeof(filesize));
					printf("%d \n", filesize);
					
					read(socket[WR], &flag, sizeof(flag));
					printf("%d \n", flag);

					if (flag == 0) {
						strcat(path, flowpath);
						strcat(path, filename);
					} else {
						strcat(path, applpath);
						strcat(path, filename);
					}

					fp = open(path, O_WRONLY | O_CREAT | O_TRUNC);

					while (total != filesize) {
						sread = (int) recv(socket[WR], buf, 100, 0);
						printf("file is receiving now.. ");
						total += sread;
						buf[sread] = 0;
						write(fp, buf, sread);
						bzero(buf, sizeof(buf));
						printf("processing : %4.2f%% ",
								total * 100 / (float) filesize);
						usleep(1000);

					}
					printf("file traslating is completed ");
					printf("filesize : %d, received : %d ", filesize, total);

					if (sread == 0) {  //if end of connection
						FD_CLR(i, &fds);
						close(fp);
						close(socket[WR]);
						printf("End connection : file descripter %d \n", i);
					}
				}
			}
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
			printf("%s : Failed to execute msg_manager", proc);
			exit(1);
		}
	}

	fd[RD] = fd1[0];
	fd[WR] = fd2[1];

	free(argv[1]);
}

void initServerSocket(int *fd) {
	struct sockaddr_in addr;
	
	if ((fd[RD] = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
		printf("%s : Failed to create socket.\n", proc);
		exit(1);
	}
	
	memset(&addr, 0, sizeof(struct sockaddr_in));
	addr.sin_family = AF_INET;
	addr.sin_addr.s_addr = htonl(INADDR_ANY);
	addr.sin_port = htons(19916);
	
	if (bind(fd[RD], (const struct sockaddr *) &addr, sizeof(addr)) < 0) {
		printf("%s : Failed to bind socket.\n", proc);
		exit(1);
	}
	
	listen(fd[RD], 5);
}

void executeFlow() {
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
				printf("%s : Failed to execute flow_executer", proc);
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
}
