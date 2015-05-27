var fs = require('fs');
var http = require('http');
var express = require('express');


// 미들웨어를 추출합니다.
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var multipart = require('connect-multiparty');
// 서버를 생성합니다.
var app = express();

// 미들웨어를 설정합니다.
app.use(cookieParser());
app.use(bodyParser.json({
    limit: '10mb'
}));
app.use(multipart({
    uploadDir: '.'
}));


// 라우터를 설정합니다.
app.get('/', function (request, response) {
    fs.readFile('HTMLPage.html', function (error, data) {
        response.send(data.toString());
    });
});
app.post('/', function (request, response) {
    // 변수를 선언합니다.
    var comment = request.param('comment');
    var imageFile = request.files.image;

    if (imageFile) {
        // 변수를 선언합니다.
        var name = imageFile.name;
        var path = imageFile.path;
        var type = imageFile.type;

      

            
            var outputPath = __dirname + '/appliance/' + name;

            fs.rename(path, outputPath, function (error) {
                response.redirect('/');
            });


    } else {
        // 파일이 없을 경우
        response.send(404);
    }
});

// 서버를 실행합니다.
http.createServer(app).listen(19918, function () {
    console.log('Server running at http://52.68.82.234:19918');
});
