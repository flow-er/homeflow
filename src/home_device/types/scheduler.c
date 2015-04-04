#include <stdio.h>
#include <dirent.h>
#include <errno.h>
#include <string.h>

#include "scheduler.h"

#define E_NOFIL "scheduler : %s : No such file or directory\n"
#define E_PERMD "scheduler : %s : Permission denied\n"

void scheduleEvents(struct scheduler *scheduler) {
	const char *path = "./user/flows/";
	DIR *flows;
	struct dirent *eBuf;

	struct event *curr = NULL;

	if (!(flows = opendir(path))) {
		//exception for no source file.
		if (errno == ENOENT)
			printf(E_NOFIL, path);
		//exception for permission denied.
		else if (errno == EACCES)
			printf(E_PERMD, path);
		//exception for no directory.
		else if (errno == ENOTDIR)
			printf("%s\n", path);

		return;
	}

	while ((eBuf = readdir(flows))) {
		char full[BUFSIZ];

		if (eBuf->d_name[0] == '.')
			continue;
		strcpy(full, path);
		strcat(full, eBuf->d_name);

		printf("%s\n", eBuf->d_name);

		if (curr) {
			curr->next = (struct event *) malloc(sizeof(struct event));
			curr = curr->next;
		} else {
			scheduler->head = (struct event *) malloc(sizeof(struct event));
			curr = scheduler->head;
		}

		curr->next = NULL;
		curr->flow = parseFlow(full);
	}
}
