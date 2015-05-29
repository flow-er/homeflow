#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <sys/errno.h>
#include <sys/types.h>

#include "types/msg.h"

void signalHandler(int);

const char *procname = "msg_manager";

int id;

int main(int argc, const char *argv[]) {
	struct message msg;
	int fd[2];

	if (argc < 3) return 0;

	signal(SIGUSR1, signalHandler);

	fd[1] = atoi(argv[1]);
	fd[0] = atoi(argv[2]);

	if ((id = msgget(MSG_KEY, MSG_PERM | IPC_CREAT)) < 0) {
		printf("%s : Can't get message queue.\n", procname);
		return 0;
	}

	while (1) {
		long msglen;
		int ret;

		msglen = msgrcv(id, &msg, MSGSIZE, 0, 0);
		if (msglen <= 0) {
			printf("%s : Can't receive message.\n", procname);
			continue;
		}

		write(fd[1], &msg, sizeof(struct message));
		read(fd[0], &ret, sizeof(int));
	}

	return 0;
}

void signalHandler(int signal) {
	msgctl(id, IPC_RMID, (struct msqid_ds *) 0);
	exit(0);
}
