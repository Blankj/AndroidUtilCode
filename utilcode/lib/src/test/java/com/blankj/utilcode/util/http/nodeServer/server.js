let express = require('express');
let fs = require("fs");
let multer = require('multer');
let bodyParser = require('body-parser');
let cookieParser = require('cookie-parser')
let util = require('util');

let app = express();
app.use(express.static('public'));
app.use(bodyParser.urlencoded({extended: false}));
app.use(multer({dest: '/tmp/'}).array('image'));
app.use(cookieParser());

// 创建 application/x-www-form-urlencoded 编码解析
let urlencodedParser = bodyParser.urlencoded({extended: false})

app.post('/process_post', urlencodedParser, function (req, res) {

    let response = {
        "first_name": req.body.first_name,
        "last_name": req.body.last_name
    };
    console.log(response);
    res.end(JSON.stringify(response));
});

app.post('/file_upload', function (req, res) {
    console.log(req.files[0]);  // 上传的文件信息

    var des_file = __dirname + "/" + req.files[0].originalname;
    fs.readFile(req.files[0].path, function (err, data) {
        fs.writeFile(des_file, data, function (err) {
            if (err) {
                console.log(err);
            } else {
                response = {
                    message: 'File uploaded successfully',
                    filename: req.files[0].originalname
                };
            }
            console.log(response);
            res.end(JSON.stringify(response));
        });
    });
});

app.get('/', function (req, res) {
    console.log("Cookies: " + util.inspect(req.cookies));
});

app.get('/index.htm', function (req, res) {
    res.sendFile(__dirname + "/" + "index.htm");
});

app.get('/process_get', function (req, res) {

    let response = {
        "first_name": req.query.first_name,
        "last_name": req.query.last_name
    };
    console.log(response);
    res.end(JSON.stringify(response));
});

// json
app.get('/listUsers', function (req, res) {
    fs.readFile(__dirname + "/" + "users.json", 'utf8', function (err, data) {
        console.log(data);
        res.end(data);
    });
});


let server = app.listen(8081, "127.0.0.1", function () {
    let host = server.address().address;
    let port = server.address().port;

    console.log("应用实例，访问地址为 http://%s:%s", host, port)
});