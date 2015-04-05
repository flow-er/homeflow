#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include "types/flow.h"

int main(int argc, const char *argv[]) {
	struct flow *flow;
	char path[BUFSIZ];
	
	if(argc < 2) return 0;
	
	sprintf(path, "./user/flow/%s.xml", argv[1]);
	
	flow = parseFlow(path);
	
	
	
	free(flow);
	return 0;
}