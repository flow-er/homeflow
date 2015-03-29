#include "flow.h"

#include <stdlib.h>
#include <string.h>

#define NEWSTRING(x, y) (strcpy((x = (char *) malloc(strlen(y))), y))

const char *nTypeName[5] = {"action",
							"notification",
							"condition",
							"loop",
							"trigger"};

void parseProperties(struct node *node, int type, xmlNode *elem);
struct node *parseNode(xmlNode *elem);

void parseFlow(struct flow *flow, const char *path, struct tm *time) {
	xmlDocPtr doc;
	xmlNode *root = NULL;
	
	xmlInitParser();
	
	doc = xmlReadFile(path, NULL, XML_PARSE_NOBLANKS);
	
	if(doc == NULL) {
		printf("ERROR : Can't parse file \'%s\'\n", path);
		return;
	}
	
	root = xmlDocGetRootElement(doc);
	
	flow->id = atoi((const char *)xmlGetProp(root, (xmlChar *)"id"));
	flow->name = (char *)xmlGetProp(root, (xmlChar *)"name");
	flow->description = (char *)xmlGetProp(root, (xmlChar *)"description");
	flow->isAuto = (!strcmp((const char *)xmlGetProp(root, (xmlChar *)"isAuto"), "true"));
	if(time) memcpy(flow->modi, time, sizeof(struct tm));
	
	flow->head = parseNode(root->children);
	
	xmlFree(doc);
	xmlFree(root);
}

struct node *parseNode(xmlNode *elem) {
	struct node *node = NULL;
	int i;
	
	for(i = 0; i < 5; i++) {
		if(!strcmp((const char *) elem->name, nTypeName[i])) {
			node = (struct node *) malloc(sizeof(struct node));
			parseProperties(node, i, elem);
			break;
		}
	}
	
	if(elem->children) node->child = parseNode(elem->children);
	if(elem->next) node->next = parseNode(elem->next);
	
	return node;
}

void parseProperties(struct node *node, int type, xmlNode *elem) {
	switch(type) {
		case CONDITION :
		case TRIGGER :
			node->cond = atoi((const char *)xmlGetProp(elem, (xmlChar *)"cond"));
			NEWSTRING(node->value, (const char *)xmlGetProp(elem, (xmlChar *)"value"));
			
		default :
			node->type = type;
			NEWSTRING(node->appid, (const char *)xmlGetProp(elem, (xmlChar *)"appid"));
			NEWSTRING(node->command, (const char *)xmlGetProp(elem, (xmlChar *)"command"));
			break;
	}
}

void printNode(struct node *node, int level) {
	int i;
	
	for(i = 0; i < level; i++) printf("   ");
	printf("|- %s\n", nTypeName[node->type]);
	
	if(node->child) printNode(node->child, level+1);
	if(node->next) printNode(node->next, level);
}

void printFlow(struct flow *flow) {
	printf("flow\n");
	
	printNode(flow->head, 0);
}