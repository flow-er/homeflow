#include <sys/msg.h>

#define MSG_KEY  1235L
#define MSG_PERM 0666

#define MSGSIZE (sizeof(struct message) - sizeof(long))
#define MESGHDRSIZE (sizeof(struct mesg) - MAXMESGDATA)

enum msgtype {
	FROM_FLOW_EXECUTER = 1,
	FROM_APP_MANAGER
};

enum state {
	FLOW_START = 1,
	FLOW_DONE,
	FLOW_FAILED,
	NODE_RUNNING,
	NODE_COMPLETED
};

struct message {
	long type;

	int id;
	int node;
	enum state state;
};
