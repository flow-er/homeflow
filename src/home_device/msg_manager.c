#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/errno.h>
#include <sys/types.h>

#include "types/msg.h"

void signalHandler(int);

int id;

int main(int argc, const char *argv[]) {
	struct message msg;
	int fd[2];

	if (argc < 3) return 0;

	signal(SIGINT, signalHandler);
	signal(SIGKILL, signalHandler);
	
	fd[1] = atoi(argv[1]);
	fd[0] = atoi(argv[2]);
	
	if ((id = msgget(MKEY, PERM | IPC_CREAT)) < 0) {
		printf("can't get message queue.\n");
		return 0;
	}

	while (1) {
		long msglen;
		int ret;
		
		msglen = msgrcv(id, &msg, MSGSIZE, 0, 0);
		if (msglen <= 0) {
			printf("can't receive message.\n");
			continue;
		}

		write(fd[1], &msg, sizeof(struct message));
		read(fd[0], &ret, sizeof(int));
	}

	return 0;
}

void signalHandler(int signal) {
	msgctl(id, IPC_RMID, (struct msqid_ds *) 0);
	exit(1);
}
