#include "flow.h"

#include <stdlib.h>
#include <string.h>

const char *nTypeName[6] = { "action", "notification", "condition", "loop",
		"cowork", "trigger" };

void parseProperties(struct node *node, enum nType type, xmlNode *elem);
struct node *parseNode(xmlNode *elem);

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
	enum nType i;

	for (i = T_ACTION; i <= T_TRIGGER; i++) {
		if (!strcmp((const char *) elem->name, nTypeName[i])) {
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

void parseProperties(struct node *node, enum nType type, xmlNode *elem) {
	const char *temp;

	node->appid = node->command = node->value = NULL;
	node->child = node->next = NULL;

	switch (type) {
		case T_CONDITION:
		case T_TRIGGER:
			temp = (const char *) xmlGetProp(elem, (xmlChar *) "cond");
			node->cond = atoi(temp);

			temp = (const char *) xmlGetProp(elem, (xmlChar *) "value");
			strcpy((node->value = (char *) malloc(strlen(temp))), temp);

		case T_ACTION:
		case T_NOTIFY:
			temp = (const char *) xmlGetProp(elem, (xmlChar *) "appid");
			strcpy((node->appid = (char *) malloc(strlen(temp))), temp);

			temp = (const char *) xmlGetProp(elem, (xmlChar *) "command");
			strcpy((node->command = (char *) malloc(strlen(temp))), temp);

		default:
			node->type = type;
			break;
	}
}

void freeNode(struct node *node) {
	if (node->child) freeNode(node->child);
	if (node->next) freeNode(node->next);
	
	free(node);
}

void freeFlow(struct flow *flow) {
	freeNode(flow->head);
}

void printNode(struct node *node, int level) {
	int i;

	for (i = 0; i < level; i++)
		printf("   ");
	printf("|%c %s\n", (node->next ? '-' : '_'), nTypeName[node->type]);

	if (node->child) printNode(node->child, level + 1);
	if (node->next) printNode(node->next, level);
}

void printFlow(struct flow *flow) {
	printf("flow\n");
	printNode(flow->head, 0);
	printf("\n");
}
