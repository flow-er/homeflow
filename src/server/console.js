var http = require('http');
var os = require('os');
var net = require('net');
var fs = require('fs');
var url = require('url');
var AWS = require('aws-sdk');
AWS.config.loadFromPath('./HomeFlow/config.json');

var paramsEnd = {
	Message: 'FLOW END',
	Subject: 'Hello',
	TopicAn:''
};
var paramsFlow = {
	Message: '등록되었습니다',
	TopicAn:''
};
var networkInterfaces = os.networkInterfaces();


var socket;
var port = 52223;

var buf1 = '';
var buf2 = '';

var order = 0;
var flag ='';


// Android HTTP POST request & GET response  
function requestHandler(req,res){
	if(req.method == 'POST'){
		var pathname = url.parse(req.url).pathname;			
	
		if(pathname == '/'){
			flag = 'FLOW';
			req.on('data',function(str){
				++order;
				buf1 += str;
			});
			
			req.on('end',function(){
				buf2 = buf1;
				buf1 = '';
				if(order == 2){
					write_File(buf2,socket,flag);	
					buf2 = '';
					order = 0;
				}
			});
					
		}else if(pathname == '/user'){
			flag = 'USER';
			req.on('data',function(str){
				buf1 += str;
			});
			req.on('end',function(){
				buf2 = buf1;
				buf1 = '';
				wirte_File(buf2,socket,flag);
				buf2 = '';
			});
	
		}else if(pathname == '/start'){
			flag = 'START';
			req.on('data',function(str){
                                buf1 += str;
                        });
			req.on('end',function(){
				buf2 = buf1;
				write_File(buf2,socket,flag);
				
			});
			
		}
		
		
	
	}else if(req.method == 'GET'){
			
	}
	
}

// File Make and Data send 
function write_File(buf,sock,flag){
	if(flag != 'START'){	
		var tmp = buf.split(' '),
	            command = tmp[0] , fileName = tmp[1],
	            xml = tmp[2] , filePath = __dirname +'/' + flag + '/' + fileName;
	    	
	
		fs.open(filePath,'w+',function(err,fd){
				if(err) throw err;
	    	});
	    	fs.writeFile(filePath,buf,function(err){
			if(err) console.log(err);
			else console.log('write end')
		});
		if(flag == 'USER') flag = '';
	}
	
	
	
	if(flag == 'FLOW' || flag == 'START'){
		if(sock){
			sock.write(buf);
			flag = '';
			console.log(flag + ' file write');
		
		}
	}
		
	
	
}


// Home Device Socekt 

function callback_server_connection(sock){
	
	++order;
	socket = sock;
	var remoteAddress = socket.remoteAddress;
	var remotePort = socket.remotePort;
	socket.setNoDelay(true);
	console.log("connected: ",remoteAddress, " : " , remotePort);
	if(order ==  2){
		write_File(buf2,sock,flag); 
		console.log('order 2 home device');
		buf2 = '';
		order = 0;
	}
	sock.addListener('data',function(data){
		console.log(data.toString());
	});
	sock.on('end',function(){
		console.log("ended: ", remoteAddress, " : ", remotePort);
	});
	
	

	
}
console.log("node.js net server is waiting:");

for(var interface in networkInterfaces) {
	networkInterfaces[interface].forEach(function(details){
	
		if((details.family == 'IPv4') && !details.internal){
			console.log(interface,details.address);
		}
	});
}

console.log("port: ",port);

var netServer = net.createServer(callback_server_connection);
netServer.listen(port);

var httpServer = http.createServer(requestHandler)
httpServer.listen(52222);

var sns = new AWS.SNS();


