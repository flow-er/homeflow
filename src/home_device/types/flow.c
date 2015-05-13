#include "flow.h"

#include <stdlib.h>
#include <string.h>

const char *nodeTypes[NODE_TN] = { "action", "condition", "loop", "cowork",
		"trigger" };
const char *condTypes[COND_TN] = { "==", "<", "<=", ">", ">=" };

struct node *parseNode(xmlNode *elem);
void parseProperties(struct node *node, enum node_t type, xmlNode *elem);
void freeNode(struct node *node);

struct flow *parseFlow(const char *path) {
	struct flow *flow = (struct flow *) malloc(sizeof(struct flow));

	xmlDocPtr doc;
	xmlNode *root = NULL;

	xmlInitParser();

	doc = xmlReadFile(path, NULL, XML_PARSE_NOBLANKS);

	if (doc == NULL) return NULL;

	root = xmlDocGetRootElement(doc);

	flow->id = atoi((const char *) xmlGetProp(root, (xmlChar *) "id"));
	flow->name = (char *) xmlGetProp(root, (xmlChar *) "name");
	flow->description = (char *) xmlGetProp(root, (xmlChar *) "description");
	flow->isAuto = (!strcmp(
			(const char *) xmlGetProp(root, (xmlChar *) "isAuto"), "true"));

	flow->head = parseNode(root->children);

	xmlFree(doc);
	xmlFree(root);

	return flow;
}

struct node *parseNode(xmlNode *elem) {
	struct node *node = NULL;
	int i;

	for (i = 0; i < NODE_TN; i++) {
		if (!strcmp((const char *) elem->name, nodeTypes[i])) {
			node = (struct node *) malloc(sizeof(struct node));
			node->child = node->next = NULL;

			parseProperties(node, i, elem);
			break;
		}
	}

	if (elem->children) node->child = parseNode(elem->children);
	if (elem->next) node->next = parseNode(elem->next);

	return node;
}

void parseProperties(struct node *node, enum node_t type, xmlNode *elem) {
	const char *temp;

	node->appid = node->command = 0;
	node->value = NULL;
	node->child = node->next = NULL;

	switch (type) {
		case T_CONDITION:
		case T_TRIGGER:
		case T_LOOP:
			temp = (const char *) xmlGetProp(elem, (xmlChar *) "cond");
			node->cond = atoi(temp);

			temp = (const char *) xmlGetProp(elem, (xmlChar *) "value");
			strcpy((node->value = (char *) malloc(strlen(temp))), temp);

		case T_ACTION:
			temp = (const char *) xmlGetProp(elem, (xmlChar *) "appid");
			node->appid = atoi(temp);

			temp = (const char *) xmlGetProp(elem, (xmlChar *) "command");
			node->command = atoi(temp);

		default:
			node->type = type;
			break;
	}
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
