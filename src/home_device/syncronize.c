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
#define BUFSIZE 1024

int main(int argc, char *argv[]) 
{ 
	typedef struct {
		long  data_type;
		int   data_num;
		char  data_buff[BUFSIZE];
	} t_data;
	int      msqid;
	t_data   data;

	fd_set readfds, temps;
	int fd_max,fd;
	struct timeval timeout;
	char* flag;

	char flowpath[40]="./user/temp/flows/";
	char applpath[40]="./user/temp/appliances/";
	char path[60];

	struct sockaddr_in serv_addr, clnt_addr; 
	int serv_sock, clnt_sock,clnt_len, // 소켓번호 
		addrlen = sizeof(clnt_addr), // 주소구조체 길이 
		nbyte, nbuf; 
	char buf[MAXLINE+1]; 
	char cli_ip[20]; 
	char filename[20]; 
	int filesize=0; 
	int total=0, sread, fp; 
	int str_len;
	// message queue //////////////////////////////////////////////////////////
	
	if ( -1 == ( msqid = msgget( (key_t)1234, IPC_CREAT | 0666)))
	{
		perror( "msgget() 실패");
		exit( 1);
	}

	while( 1 )
	{
		// Receive data whish data_type is 2 
		if ( -1 == msgrcv( msqid, &data, sizeof( t_data) - sizeof( long), 2, 0))
		{
			perror( "msgrcv() 실패");
			exit( 1);
		}
		printf( "%d - %s\n", data.data_num, data.data_buff);
	}
	
	///////////////////////////////////////////////////////////////////////

	if(argc != 2) { 
		printf("usage: %s port ", argv[0]); 
		exit(0); 
	} 
	// Create socket
	if((serv_sock = socket(PF_INET, SOCK_STREAM, 0)) < 0) { 
		perror("socket fail"); 
		exit(0); 
	} 
	
	bzero((char *)&serv_addr, sizeof(serv_addr)); 
	// Set the serv_addr 
	serv_addr.sin_family = AF_INET; 
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY); 
	serv_addr.sin_port = htons(atoi(argv[1])); 

	// bind()  
	if(bind(serv_sock, (struct sockaddr *)&serv_addr, 
		sizeof(serv_addr)) < 0) 
	{ 
		perror("bind fail"); 
		exit(0); 
	} 
	// listen()
	listen(serv_sock, 5); 

	FD_ZERO(&readfds);
	FD_SET(serv_sock, &readfds);
	fd_max=serv_sock;

	while(1) 
	{ 
		puts("waiting connection..\n"); 

		temps=readfds;
		timeout.tv_sec = 5;
		timeout.tv_usec = 0; 

		if(select(fd_max+1, &temps, 0, 0, &timeout)==-1)
		{
			printf("select error\n");
			exit(0);
		}

		for(fd=0; fd<fd_max+1; fd++)
		{
			if(FD_ISSET(fd, &temps))
			{
				if(fd==serv_sock)
				{
					clnt_len = sizeof(clnt_addr);
					clnt_sock = accept(serv_sock, 
						(struct sockaddr *)&clnt_addr, &addrlen);

					if(clnt_sock < 0) 
					{ 
						perror("accept fail"); 
						exit(0); 
					} 
					FD_SET(clnt_sock, &readfds);
					if(fd_max<clnt_sock)
						fd_max=clnt_sock;
					printf("Connection : file descripter %d \n", clnt_sock);
					inet_ntop(AF_INET, &clnt_addr.sin_addr.s_addr, cli_ip, sizeof(cli_ip)); 
					printf( "IP : %s ", cli_ip ); 
					printf( "Port : %x \n", ntohs( clnt_addr.sin_port) ); 
				}
				else 
				{
					bzero( filename, 20 ); 
					recv( clnt_sock, filename, sizeof(filename), 0 ); 
					printf( "%s ", filename ); 

					// recv( clnt_sock, &filesize, sizeof(filesize), 0 ); 
					read( clnt_sock, &filesize, sizeof(filesize) ); 
					printf( "%d \n", filesize ); 

					strcat( filename, "_backup" ); 
					fp = open( filename, O_WRONLY | O_CREAT | O_TRUNC); 

					while( total != filesize ) 
					{ 
						sread = recv( clnt_sock, buf, 100, 0 ); 
						printf( "file is receiving now.. " ); 
						total += sread; 
						buf[sread] = 0; 
						write( fp, buf, sread ); 
						bzero( buf, sizeof(buf) ); 
						printf( "processing : %4.2f%% ", total*100 / (float)filesize ); 
						usleep(1000); 

					} 
					printf( "file traslating is completed " ); 
					printf( "filesize : %d, received : %d ", filesize, total ); 

					
					if(sread ==0)
					{ /* if end of connection  */
						FD_CLR(fd, &readfds);
						close(fp); 
						close(clnt_sock); 
						printf("End connection : file descripter %d \n", fd);
					}
					else
					{
						write(serv_sock,data.data_buff,strlen(data.data_buff)+1);
					}	
				} // end else
			} // end if
		} // end for			
	} // end while

	close( serv_sock ); 
	return 0; 
} // end main
