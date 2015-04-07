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
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/types.h>

#include "types/scheduler.h"
#include "types/msg.h"

#define MAXLINE 127

int executeMsgManager(int *fd);
int executeFlow();
void signalHandler(int);

enum fdType {
	R_PIPE = 0,
	W_PIPE,
	R_CONN
};

struct scheduler scheduler;
int fd[3];

int main(int argc, const char *argv[]) {
	typedef struct {
		long data_type;
		int data_num;
		char data_buff[BUFSIZ];
	} t_data;
	t_data data;

	int count;

	struct sockaddr_in serv_addr, clnt_addr;
	struct timeval timeout;
	int clnt_sock;
	socklen_t addrlen = sizeof(clnt_addr);

	fd_set readfds;
	int fd_max;
	int filesize = 0;
	int fp;
	int total, sread = 0;
	char buf[MAXLINE + 1];

	int flag = 0;
	char flowpath[40] = "./user/temp/flows/";
	char applpath[40] = "./user/temp/appliances/";

	char cli_ip[20], filename[20], path[60];

	signal(SIGINT, signalHandler);

	scheduleEvents(&scheduler, INIT);

	if (executeMsgManager(fd) == -1) {
		printf("flow_manger : failed to execute \'msg_manager\'\n");
		return 0;
	}

	if ((fd[R_CONN] = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
		printf("Error on creating socket.\n");
		return 0;
	}

	memset(&serv_addr, 0, sizeof(struct sockaddr_in));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	serv_addr.sin_port = htons(0);  //use android port instead of 0

	if (bind(fd[R_CONN], (const struct sockaddr *) &serv_addr,
				sizeof(serv_addr)) < 0) {
		printf("Error on binding socket.\n");
		return 0;
	}

	listen(fd[R_CONN], 5);

	FD_ZERO(&readfds);
	FD_SET(fd[R_CONN], &readfds);
	fd_max = fd[R_CONN];

	while (1) {
		fd_set temp = readfds;
		int i;
		timeout.tv_sec = 5;
		timeout.tv_usec = 0;

		if (executeFlow() == 1) {
			printf("flow_manger : failed to execute \'flow_executer\'\n");
			return 0;
		}

		if (select(fd_max + 1, &temp, 0, 0, &timeout) < 0) {
			printf("Error on select.\n");
			return 0;
		}

		for (i = 0; i < fd_max + 1; i++) {
			if (FD_ISSET(i, &temp)) {
				if (i == fd[R_CONN]) {
					int clnt_len = sizeof(clnt_addr);
					int clnt_sock = accept(fd[R_CONN],
											(struct sockaddr *) &clnt_addr,
											&addrlen);

					if (clnt_sock < 0) {
						perror("accept fail");
						exit(0);
					}

					FD_SET(clnt_sock, &readfds);
					if (fd_max < clnt_sock) fd_max = clnt_sock;
					printf("Connection : file descripter %d \n", clnt_sock);
					inet_ntop(AF_INET, &clnt_addr.sin_addr.s_addr, cli_ip,
								sizeof(cli_ip));
					printf("IP : %s ", cli_ip);
					printf("Port : %x \n", ntohs(clnt_addr.sin_port));
				} else {
					memset(filename, 0, 20);
					memset(path, 0, 60);
					recv(clnt_sock, filename, sizeof(filename), 0);
					printf("%s", filename);

					// recv( clnt_sock, &filesize, sizeof(filesize), 0 );
					read(clnt_sock, &filesize, sizeof(filesize));
					printf("%d \n", filesize);

					read(clnt_sock, &flag, sizeof(flag));
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
						sread = (int) recv(clnt_sock, buf, 100, 0);
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
						FD_CLR(i, &readfds);
						close(fp);
						close(clnt_sock);
						printf("End connection : file descripter %d \n", i);
					} else {
						// Send the flow statue to android
						while (count > 0) {
							write(fd[R_CONN], data.data_buff,
									strlen(data.data_buff) + 1);
						}
						//initialize the message buffer
						memset(data.data_buff, 0, BUFSIZ);
					}
				}
			}
		}
	}

	return 0;
}

int executeMsgManager(int *fd) {
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
	sprintf(argv[1], "%d", fd2[0]);

	if ((pid = fork())) {
		close(fd1[1]);
		close(fd2[0]);
		printf("msg_manager : %d\n", pid);
	} else {
		close(fd1[0]);
		close(fd2[1]);

		if (execv("./msg_manager", argv) == -1) {
			return 1;
		}
	}

	fd[R_PIPE] = fd1[0];
	fd[W_PIPE] = fd2[1];

	free(argv[1]);
	return 0;
}

int executeFlow() {
	struct event *event = NULL;

	for (event = scheduler.head; event != NULL; event = event->next) {
		char *argv[3];

		if (event->pid != 0) continue;
		if (!event->flow->isAuto) continue;

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
	struct event *event = NULL;
	int i;

	for (event = scheduler.tail; event != NULL; event = event->prev) {
		freeFlow(event->flow);
		free(event);
	}

	for (i = 0; i < 3; i++)
		close(fd[i]);
}
