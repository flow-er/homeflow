all : flow_manager flow_executer msg_manager app_manager gatt_conn

flow_manager :
	gcc -o ../flow_manager `xml2-config --cflags` \
	       flow_manager.c types/flow.c types/scheduler.c \
	       `xml2-config --libs`

flow_executer :
	gcc -o ../flow_executer `xml2-config --cflags` \
	       flow_executer.c types/flow.c types/appliance.c \
	       `xml2-config --libs`

msg_manager :
	gcc -o ../msg_manager msg_manager.c

app_manager :
	gcc -o ../app_manager `xml2-config --cflags` \
	       app_manager.c types/appliance.c \
	       `xml2-config --libs`

gatt_conn :
	cp gatt_conn.sh ../

clean :
	rm -rf ../flow_manager ../flow_executer ../msg_manager ../app_manager ../gatt_conn.sh
