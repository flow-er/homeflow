#include <stdio.h>
#include <time.h>

#include <libxml/xmlmemory.h>
#include <libxml/parser.h>

#if __STDC_VERSION__ >= 199901L
	#include <stdbool.h>
#else
	#define bool int;
	#define true 1
	#define false 0
#endif

//types for flow node
#define FN_ACTN 0
#define FN_NOTI 1
#define FN_COND 2
#define FN_LOOP 3

//types for flow trigger
#define FT_TIME 0

typedef xmlNode node;

enum dayweek {SUN = 0, MON, TUE, WED, THU, FRI, SAT};

struct trigger {
	//properties
	//by union
	
	struct trigger *next;
};

struct flow {
	int id;
	char *name;
	char *description;
	bool isAuto;
	
	struct trigger *trig;
	
	node *head;
};

void parseFlow(const char *xml, struct flow *flow);
void freeFlow(struct flow *flow);



//OLD DEFINITIONS
//
//union properties {
//	//action * notification
//	struct {
//		int appid;
//		int command;
//	};
//	//condition
//	struct {
//		//
//	};
//	//loop
//	struct {
//		int count;
//	};
//};
//
//struct node {
//	int type;
//	union properties prop;
//
//	struct node *next;
//	struct node *include;
//};
//
//struct trigger {
//	int type;
//};
//
//struct flow {
//	int id;
//
//	struct node *head;
//};
