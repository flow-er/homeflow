#include <stdio.h>

#include <libxml/xmlmemory.h>
#include <libxml/parser.h>

#include "types/appliance.h"
#include "types/msg.h"

void signalHandler(int);

const char *procname = "app_manager";

int main(int argc, const char *argv[]) {
	signal(SIGUSR1, signalHandler);

	return 0;
}

void signalHandler(int) {
	exit(0);
}
