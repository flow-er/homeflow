#define MKEY 1234L
#define PERM 0666

#define MSGSIZE (sizeof(struct message) - sizeof(long))
#define MESGHDRSIZE (sizeof(struct mesg) - MAXMESGDATA)

enum state {
	FLOW_RUNNING = 0,
	FLOW_DONENODE,
	FLOW_COMPLETED,
	FLOW_FAILED,
};

struct message {
	long pid;
	enum state state;
};
