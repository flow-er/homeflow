#include <sys/msg.h>

#define MSG_KEY  1234L
#define MSG_PERM 0666

#define MSGSIZE (sizeof(struct message) - sizeof(long))
#define MESGHDRSIZE (sizeof(struct mesg) - MAXMESGDATA)

enum state {
	FLOW_RUNNING = 0,
	FLOW_DONENODE,
	FLOW_SKIPCHILD,
	FLOW_COMPLETED,
	FLOW_FAILED,
};

struct message {
	long type;
	pid_t pid;
	
	int node;
	enum state state;
};
