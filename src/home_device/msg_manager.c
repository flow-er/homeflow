#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/errno.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/types.h>

#include "types/msg.h"

int main(int argc, const char *argv[]) {
	struct message msg;

	int fd = atoi(argv[1]);
	int id;

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

		//TEST CODE
		printf("flow pid   : %ld", msg.pid);
		printf("flow state : %d", msg.state);
		//TEST END

		write(fd, &msg, sizeof(struct message));
		read(fd, &ret, sizeof(int));
	}

	return 0;
}
