#include <stdio.h>
#include <string.h>
#include "types/flow.h"

int main(int argc, const char *argv[]) {
	struct flow *flow;
	char file[BUFSIZ];
	
	if(argc < 2) return 0;
	
	strcpy(file, argv[1]);
	strcat(file, ".xml");
	
	flow = parseFlow(file);
	
	printFlow(flow);
	return 0;
}