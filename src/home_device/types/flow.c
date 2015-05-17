#include "flow.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <libxml/xmlmemory.h>
#include <libxml/parser.h>

#define GETPROP(x, y) ((const char *) xmlGetProp(x, (xmlChar *) y))

const char *types[NT] = { "action", "condition", "loop", "cowork", "trigger" };

struct node *parseNode(xmlNode *elem);
void freeNode(struct node *node);

struct flow *parseFlow(const char *path) {
	struct flow *flow;

	xmlDocPtr doc;
	xmlNode *root = NULL;

	xmlInitParser();

	doc = xmlReadFile(path, NULL, XML_PARSE_NOBLANKS);
	if (!doc) return NULL;

	root = xmlDocGetRootElement(doc);

	// Initialize the flow.
	flow = (struct flow *) malloc(sizeof(struct flow));

	// Parse properties and child nodes of the flow.
	flow->id = atoi(GETPROP(root, "id"));
	flow->isAuto = (!strcmp(GETPROP(root, "isAuto"), "true"));
	flow->head = parseNode(root->children);

	xmlFree(doc);
	xmlFree(root);

	return flow;
}

struct node *parseNode(xmlNode *elem) {
	struct node *node = NULL;
	int type;

	for (type = 0; type < NT; type++) {
		if (!strcmp((const char *) elem->name, types[type])) {
			// Initialize the node.
			node = (struct node *) malloc(sizeof(struct node));

			node->type = type;
			node->appid = node->command = node->option = 0;
			node->child = node->next = NULL;

			// Parse properties of the node. (except cowork node)
			if (node->type == N_COWORK) break;

			node->appid = atoi(GETPROP(elem, "appid"));
			node->command = (uint) atoi(GETPROP(elem, "command"));
			node->option = (uint) atoi(GETPROP(elem, "option"));
			break;
		}
	}

	if (elem->children) node->child = parseNode(elem->children);
	if (elem->next) node->next = parseNode(elem->next);

	return node;
}

void freeFlow(struct flow *flow) {
	freeNode(flow->head);
	free(flow);
}

void freeNode(struct node *node) {
	if (node->child) freeNode(node->child);
	if (node->next) freeNode(node->next);

	free(node);
}
