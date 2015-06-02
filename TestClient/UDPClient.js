var PORT = 9090;
var HOST = '127.0.0.1';

var dgram = require('dgram');

data = '{ ' +
'"Id" : "i00${i + 1}", ' +
'"Name" : "Customer", ' +
'"Type" : "JMS", ' +
'"Status" : "OK", ' +
'"data": ' +
'[ ' +
'{ "name" : "msgRecv", "value" : "1", "type" : "T" }, ' +
'{ "name": "msgSend", "value" : "5", "type" : "C" }, ' +
'{ "name": "Errors", "value" : "13", "type" : "V" }, ' +
'{ "name": "ErrorXSLT", "value" : "true", "type" : "B" }, ' +
'{ "name": "LastError", "value" : "NO ERRORS", "type" : "S" } ' +
'] ' +
'}';

var message = new Buffer(data);

var client = dgram.createSocket('udp4');
client.send(message, 0, message.length, PORT, HOST, function(err, bytes) {
    if (err) throw err;
    console.log('UDP message sent to ' + HOST +':'+ PORT);
    client.close();
});
