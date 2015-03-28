#include "flow.h"

#include <stdlib.h>
#include <string.h>

void parseFlow(const char *path, struct flow *flow) {
	xmlDocPtr doc;
	xmlNode *root = NULL;
	
	xmlInitParser();
	
	doc = xmlReadFile(path, NULL, 0);
	
	if(doc == NULL) {
		printf("ERROR : Can't parse file \'%s\'\n", path);
		return;
	}
	
	root = xmlDocGetRootElement(doc);
	
	flow->id = atoi((const char *)xmlGetProp(root, (xmlChar *)"id"));
	flow->name = (char *)xmlGetProp(root, (xmlChar *)"name");
	flow->description = (char *)xmlGetProp(root, (xmlChar *)"description");
	flow->isAuto = (!strcmp((const char *)xmlGetProp(root, (xmlChar *)"isAuto"), "true"));
	
	if(flow->isAuto) {
		root = root->children;
		while(strcmp((const char *)root->name, "trigger")) root = root->next;
		
		
	}
}

void freeFlow(struct flow *flow) {
	xmlFree(flow->head);
	xmlFree(flow->head->parent);
	free(flow);
}

//OLD DEFINITIONS
//
//void parseElement(xmlNode *elem, struct node *node, int level) {
//	int i, j;
//	
//	for(i = 0; i < 5; i++) {
//		if(!strcmp((const char *) elem->name, tName[i])) {
//			
//			for(j = 0; j < level; j++) printf("  ");
//			printf("|- %s\n", elem->name);
//			
//			parseProperties(elem, node, level);
//			break;
//		}
//	}
//	
//	if(elem->children) parseElement(elem->children, node->include, level+1);
//	if(elem->next) parseElement(elem->next, node->next, level);
//}
//
//void parseProperties(xmlNode *elem, struct node *node, int level) {
//	int i, j;
//	
//	for(i = 0; i < sizeof(prop)/sizeof(char *); i++) {
//		for(j = 0; j < level; j++) printf("  ");
//		
//		printf("    %s : %s\n", prop[i], xmlGetProp(elem, (xmlChar *)prop[i]));
//	}
//}