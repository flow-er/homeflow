#include "appliance.h"

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>

#include <libxml/xmlmemory.h>
#include <libxml/parser.h>

#define GETPROP(x, y) ((const char *) xmlGetProp(x, (xmlChar *) y))

struct appliance *parseApp(xmlNode *elem);

int sendCommandToBLuetoohDevice(long addr, cmdset set, int *ret);
int sendCommandToVirtualDevice(long addr, cmdset set, int *ret);

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
	app->id = atoi(GETPROP(elem, "appid"));
	app->ctype = atoi(GETPROP(elem, "ctype"));
	app->addr = atol(GETPROP(elem, "addr"));

	switch (app->ctype) {
		case C_BLUE:
			app->runCommand = sendCommandToBLuetoohDevice;
			break;

		case C_VIRTUAL:
			app->runCommand = sendCommandToVirtualDevice;
			break;
	}

	if (elem->next) app->next = parseNode(elem->next);
	app->next->prev = app;

	return app;
}

void freeApplList(struct appl_list *apps) {
	struct appliance *app = NULL;

	for (app = apps->tail; app != NULL; app = app->prev) {
		free(app);
	}

	free (scheduler);
}

int sendCommandToBLuetoohDevice(long addr, cmdset set, int *ret) {
	printf("test : press any button to continue.\n");
	getchar();

	return 0;
}

int sendCommandToVirtualDevice(long addr, cmdset set, int *ret) {
	enum cond_t cond;
	int value;

	// Parse command option.
	cond = set.option >> 24;
	value = (set.option << 8) >> 8;

	switch (addr) {
		// Available only for 'O_NOWAIT'.
		case V_COUNTER:
			if (set.command == 1) {
				static int count = -1;
				int result = 0;

				++count;

				if (count == value) result += (1 << 0);
				if (count < value) result += (1 << 1);
				if (count > value) result += (1 << 2);

				*ret = (result & cond);

				if (*ret == 0) count = -1;
			}
			break;

			// Available only for 'O_NOWAIT' and 'O_WAIT'.
		case V_TIMER:
			if (set.command == 1) {
				struct tm *temp = localtime(&time(NULL));
				int wday_mask = value >> 17;

				long goal = 0, curr = 0;

				int result = 0;

				if (!(wday_mask & (1 << curr.tm_wday))) {
					*ret = 0;
					return 0;
				}

				goal += ((value << 7) >> 19) * 3600;
				goal += ((value << 12) >> 18) * 60;
				goal += ((value << 18) >> 18);

				curr += temp->tm_hour * 3600;
				curr += temp->tm_min * 3600;
				curr += temp->tm_sec;

				if (set.type == O_NOWAIT) {
					if (curr == goal) result += (1 << 0);
					if (curr < goal) result += (1 << 1);
					if (curr > goal) result += (1 << 2);

					*ret = (result & cond);
				} else if (set.type == O_WAIT) {
					// Available only for '=='.

					if (curr > goal) {
						*ret = 0;
						return 0;
					}

					sleep((goal - curr) * 1000);
					*ret = 1;
				}
			}
			break;
	}

	return 0;
}
