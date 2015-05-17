#include "appliance.h"

#include <stdio.h>
#include <stdlib.h>

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
		case V_COUNTER:
			if (set.command == 1) {
				static int count = -1;
				int result = 0;

				++count;

				if (count == value) result += (1<<0);
				if (count < value)  result += (1<<1);
				if (count > value)  result += (1<<2);

				*ret = (result & cond);
			}
			break;

		case V_TIMER:
			if (set.command == 1) {
//				struct tm time;
//				mktime(&time);
			}
			break;
	}

	return 0;
}
