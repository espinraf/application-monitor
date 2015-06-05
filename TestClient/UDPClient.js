var PORT = 9090;
var HOST = '127.0.0.1';

var dgram = require('dgram');
var readline = require('readline');

data = '{ ' +
'"Id" : "i00JS", ' +
'"Name" : "Nodejs", ' +
'"Type" : "JS", ' +
'"Status" : "OK", ' +
'"data": ' +
'[ ' +
'{ "name" : "Requests", "value" : "1", "type" : "T" }, ' +
'{ "name": "Response", "value" : "5", "type" : "C" }, ' +
'{ "name": "Errors", "value" : "13", "type" : "V" }, ' +
'{ "name": "ErrorHTTP", "value" : "true", "type" : "B" }, ' +
'{ "name": "LastError", "value" : "NO ERRORS", "type" : "S" } ' +
'] ' +
'}';

var message = new Buffer(data);


var client;

var rl = readline.createInterface(process.stdin, process.stdout);
rl.setPrompt('$ ');
rl.prompt();

rl.on('line', function(line) {
    if (line === "exit") rl.close();
client = dgram.createSocket('udp4');
client.send(message, 0, message.length, PORT, HOST, function(err, bytes) {
    if (err) throw err;
    console.log('UDP message sent to ' + HOST +':'+ PORT);
    client.close();
});
    rl.prompt();
}).on('close',function(){
    process.exit(0);
});