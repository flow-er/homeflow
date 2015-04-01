#include <stdio.h>
#include "types/flow.h"
#include <io.h>
#include <errno.h>
#include <time.h>
#include <signal.h>
#include <sys/types.h>



void Signal_INT_Handler( int ) ;//refreshing func 



int main(int argc, const char *argv[]) {

	signal(SIGINT, Signal_INT_Handler);
	return 0;
}


/**
* @classname Signal_INT_Handler
* @brief refreshing xml files
* @details compare old file's information with new one and refresh new file's information (flows)
* @param (signal)
* @author Choi won beom, Kim hoyunjigi
* @version 0.1
*/
void Signal_INT_Handler(int num)
{
	_finddatai64_t c_file;
	intptr_t hFile;
	struct tm *t;
	char path[] = "*.*"; // flow directory


	if ( (hFile = _findfirsti64(path, &c_file)) == -1L ) {
		switch (errno) {
		case ENOENT:
			printf(":: No files in this directory ::\n"); break;
		case EINVAL:
			fprintf(stderr, "Invalid path name.\n"); exit(1); break;
		case ENOMEM:
			fprintf(stderr, "Not enough memory or file name too long.\n"); exit(1); break;
		default:
			fprintf(stderr, "Unknown error.\n"); exit(1); break;
		}
	} // end if
	else {
		do {
			t = localtime(&c_file.time_write);

			if((t->tm_sec != tm->tm_sec && t->tm_min != tm->tm_min && t->tm_hour!= tm->tm_hour))   
			{
				flow = (struct flow *) malloc(sizeof(struct flow));
				parseFlow(flow, const char *path, t);
			}


		} while (_findnexti64(hFile, &c_file) == 0);
	} // end else
	_findclose(hFile);
}

