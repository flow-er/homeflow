#include <sys/msg.h>

#define MSG_KEY  1234L
#define MSG_PERM 0666

#define MSGSIZE (sizeof(struct message) - sizeof(long))
#define MESGHDRSIZE (sizeof(struct mesg) - MAXMESGDATA)

enum msgtype {
	FROM_FLOW_EXECUTER = 0,
	FROM_APP_MANAGER
};

enum state {
	FLOW_RUNNING = 0,
	FLOW_COMPLETED,
	FLOW_FAILED,
	NODE_RUNNING,
	NODE_COMPLETED
};

struct message {
	long type;
	pid_t pid;

	int node;
	enum state state;
};
