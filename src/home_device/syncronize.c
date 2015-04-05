#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <sys/types.h> 
#include <sys/socket.h> 
#include <netinet/in.h> 
#include <unistd.h> 
#include <fcntl.h> 
#include <sys/ipc.h>
#include <sys/msg.h>

#define MAXLINE 127 
#define PORT 5555
#define BUFSIZE 1024

int main() {
	typedef struct {
		long data_type;
		int data_num;
		char data_buff[BUFSIZE];
	} t_data;
	int msqid;
	t_data data;

	struct sockaddr_in servaddr, cliaddr;
	int serv_sock, client_sock,  // socket number
			addrlen = sizeof(cliaddr), nbyte, nbuf;
	char buf[MAXLINE + 1];
	char cli_ip[20];
	char flowpath[40] = "./user/temp/flows/";
	char applpath[40] = "./user/temp/appliances/";
	char filename[20];
	char path[60];
	int filesize = 0;
	int total = 0, sread, fp;
	//int count=0;
	fd_set readfds, writefds, tmpfds, tmp2fds;
	int fd_max, fd;
	struct timeval timeout;
	char* flag;

	// message queue //////////////////////////////////////////////////////////
	if ( -1 == ( msqid = msgget( (key_t)1234, IPC_CREAT ¦ 0666))) {
		perror("msgget() 실패");
		exit(1);
	}

	while (1) {
		// 메시지 큐 중에 data_type 이 2 인 자료만 수신
		if (-1 == msgrcv(msqid, &data, sizeof(t_data) - sizeof(long), 2, 0)) {
			perror("msgrcv() 실패");
			exit(1);
		}
		printf("%d - %s\n", data.data_num, data.data_buff);
	}
	///////////////////////////////////////////////////////////////////////

	if ((serv_sock = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
		perror("socket fail");
		exit(0);
	}
	// initialization
	bzero((char *) &servaddr, sizeof(servaddr));
	// set the server address
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	servaddr.sin_port = htons(PORT)
	);

	/*
	 bind()
	 */
	if (bind(serv_sock, (struct sockaddr *) &servaddr, sizeof(servaddr)) < 0) {
		perror("bind fail");
		exit(0);
	}
	/*
	 listen()
	 */
	if (listen(serv_sock, 5) < 0) {
		perror("listen fail");
		exit(0);
	}

	FD_ZERO(&readfds);
	FD_ZERO(&writefds);
	FD_SET(serv_sock, &readfds);
	fd_max = serv_sock;

	while (1) {
		//printf("...\n");

		timeout.tv_sec = 5;
		timeout.tv_usec = 0;

		tmpfds = readfds;
		tmp2fds = writefds;

		if (select(fd_max + 1, &tmpfds, &tmp2fds, 0, &timeout) == -1) {
			perror("select fail");
			exit(0);
		}

		for (fd = 0; fd < fd_max + 1; fd++) {
			if (FD_ISSET(fd, &tmpfds)) {
				if (fd == serv_sock) {
					/*
					 accept()
					 */
					client_sock = accept(serv_sock,
											(struct sockaddr *) &cliaddr,
											&addrlen);
					FD_SET(client_sock, &readfds);
					if (client_sock < 0) {
						perror("accept fail");
						exit(0);
					}

					printf("Connecting success\n");

					inet_ntop(AF_INET, &cliaddr.sin_addr.s_addr, cli_ip,
								sizeof(cli_ip));
					// print cli's info
					printf("IP : %s ", cli_ip);
					printf("Port : %x ", ntohs(cliaddr.sin_port));
				} else {
					bzero(filename, 20);
					recv(client_sock, filename, sizeof(filename), 0);
					printf("%s ", filename);

					// recv( client_sock, &filesize, sizeof(filesize), 0 ); 
					read(client_sock, &filesize, sizeof(filesize));
					printf("%d ", filesize);

					read(client_sock, flag, sizeof(flag));
					if (strcmp(flag, "f")) {
						strcat(path, flowpath);
						strcat(path, filename);
					} else {
						strcat(path, applpath);
						strcat(path, filename);
					}

					fp = open(path, O_WRONLY | O_CREAT | O_TRUNC);

					while (total != filesize) {
						sread = recv(client_sock, buf, 100, 0);
						printf("file is receiving now.. \n");
						total += sread;
						buf[sread] = 0;
						write(fp, buf, sread);
						bzero(buf, sizeof(buf));
						printf("processing : %4.2f%% ",
								total * 100 / (float) filesize);
						usleep(1000);

					}
					printf("file traslating is completed \n");
					printf("filesize : %d, received : %d \n", filesize, total);

					FD_CLR(fd, &readfds);
					//close(fp); 
					close(client_sock);
				}  //end else
			}  //end if 
			else if (FD_ISSET(fd, &tmp2fds)) {
				if (connect(serv_sock, (struct sockaddr *) &servaddr,
							sizeof(servaddr)) < 0) {
					perror("connect fail");
					exit(0);
				}
				write(serv_sock, data.data_buff, strlen(data.data_buff) + 1);

			}  //end else if
		}  //end for
	}  //end while

	close(serv_sock);
	return 0;
}
