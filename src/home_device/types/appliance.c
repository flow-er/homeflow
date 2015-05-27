#include "appliance.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <arpa/inet.h>

#include <libxml/xmlmemory.h>
#include <libxml/parser.h>

#define GETPROP(x, y) ((const char *) xmlGetProp(x, (xmlChar *) y))

typedef int fd;

struct appliance *parseApp(xmlNode *elem);

int sendCommandToVirtualDevice(char *addr, cmdset set);
int setCommandToVirtualMultiSensor(char *addr, cmdset set);
int sendCommandToBLuetoohDevice(char *addr, cmdset set);

struct appl_list *parseApplList(const char *path) {
	struct appl_list *apps;

	xmlDocPtr doc;
	xmlNode *root = NULL;

	xmlInitParser();

	doc = xmlReadFile(path, NULL, XML_PARSE_NOBLANKS);
	if (!doc) return NULL;

	root = xmlDocGetRootElement(doc);

	// Initialize the flow.
	apps = (struct appl_list *) malloc(sizeof(struct appl_list));

	// Parse child nodes of the appliance-list.
	apps->head = parseApp(root->children);

	xmlFree(doc);
	xmlFree(root);

	return apps;
}

struct appliance *parseApp(xmlNode *elem) {
	struct appliance *app;

	// Initialize the appliance.
	app = (struct appliance *) malloc(sizeof(struct appliance));
	app->prev = app->next = NULL;

	// Parse properties of the appliance.
	app->id = atoi(GETPROP(elem, "id"));
	app->ctype = atoi(GETPROP(elem, "ctype"));
	strcpy(app->addr, GETPROP(elem, "addr"));

	switch (app->ctype) {
		case C_VIRTUAL:
			app->runCommand = sendCommandToVirtualDevice;
			break;
		case C_VMULTI:
			app->runCommand = setCommandToVirtualMultiSensor;
			break;
		case C_BLUE:
			app->runCommand = sendCommandToBLuetoohDevice;
			break;
	}

	if (elem->next) {
		app->next = parseApp(elem->next);
		app->next->prev = app;
	}

	return app;
}

void freeApplList(struct appl_list *apps) {
	struct appliance *app = NULL;

	for (app = apps->tail; app != NULL; app = app->prev) {
		free(app);
	}

	free(apps);
}

int sendCommandToVirtualDevice(char *addr, cmdset set) {
	enum cond_t cond = set.option >> 24;
	uint value = (set.option << 8) >> 8;

	switch (atoi(addr)) {
		case V_TIMER:  // Available only for 'O_NOWAIT' and 'O_WAIT'.
			if (set.command == 1) {
				time_t t = time(NULL);
				struct tm *temp = localtime(&t);
				int wday_mask = value >> 17;

				uint goal = 0, curr = 0;

				if (!(wday_mask & (1 << (6 - temp->tm_wday)))) return 0;

				goal += ((value << 15) >> 27) * 3600;
				goal += ((value << 20) >> 26) * 60;
				goal += ((value << 26) >> 26);

				curr += temp->tm_hour * 3600;
				curr += temp->tm_min * 60;
				curr += temp->tm_sec;

				if (set.type == O_NOWAIT) {
					int result = 0;

					if (curr == goal) result += (1 << 0);
					if (curr < goal) result += (1 << 1);
					if (curr > goal) result += (1 << 2);

					return (result & cond);
				} else if (set.type == O_WAIT) {
					// Available only for '=='.

					if (curr > goal) return 0;

					sleep((goal - curr) * 1000);
					return 1;
				}
			}
			break;

		case V_COUNTER:  // Available only for 'O_NOWAIT'.
			if (set.command == 1) {
				static int count = 0;
				int result = 0;

				++count;

				if (count == value) result += (1 << 0);
				if (count < value) result += (1 << 1);
				if (count > value) result += (1 << 2);

				if (!(result & cond)) count = -1;
				return (result & cond);
			}
			break;
	}

	return 0;
}

int setCommandToVirtualMultiSensor(char *addr, cmdset set) {
	enum cond_t cond = set.option >> 24;
	uint value = (set.option << 8) >> 8;
	int curr = -1;

	if (set.type == O_NOWAIT) {
		int result = 0;

		scanf("%d", &curr);

		if (curr == value) result += (1 << 0);
		if (curr < value) result += (1 << 1);
		if (curr > value) result += (1 << 2);

		return (result & cond);
	} else if (set.type == O_WAIT) {
		int result = 0;

		do {
			result = curr = 0;

			scanf("%d", &curr);

			if (curr == value) result += (1 << 0);
			if (curr < value) result += (1 << 1);
			if (curr > value) result += (1 << 2);
		} while (!(result & cond));

		return 1;
	}

	return 1;
}

int sendCommandToBLuetoohDevice(char *addr, cmdset set) {
	// NOTE : ABLE TO HAVE THIS PROBLEM!!!
	//        connect can be failed if more than one processess try to connect
	//        with a specific ble device.

	enum cond_t cond = set.option >> 24;
	uint value = (set.option << 8) >> 8;
	int curr = -1;

	pid_t pid;
	char *argv[5] = { "sh", "gatt_conn.sh", addr, NULL, NULL };

	argv[3] = (char *) malloc(sizeof(char) * BUFSIZ);
	sprintf(argv[3], "%x", set.command);

	if (set.type == O_NOWAIT) {
		int result = 0;

		if ((pid = fork())) {
			wait(&curr);
		} else {
			if (execvp("sh", argv) == -1) {
				printf("flow_executer : Failed to execute gatt tool\n");
				exit(1);
			}
		}

		if (curr == value) result += (1 << 0);
		if (curr < value) result += (1 << 1);
		if (curr > value) result += (1 << 2);

		return (result & cond);
	} else if (set.type == O_WAIT) {
		int result;

		do {
			result = curr = 0;

			if ((pid = fork())) {
				wait(&curr);
			} else {
				if (execvp("sh", argv) == -1) {
					printf("flow_executer : Failed to execute gatt tool\n");
					exit(1);
				}
			}

			if (curr == value) result += (1 << 0);
			if (curr < value) result += (1 << 1);
			if (curr > value) result += (1 << 2);
		} while (!(result & cond));

		return 1;
	} else {
		return curr;
	}
}
