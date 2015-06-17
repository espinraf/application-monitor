
addr = InetAddress.getByName("localhost")
port = 9090

//def rn = java.util.Random();
def socket = new DatagramSocket()
def rannum = 1;
def rannum2 = 2;
while (true){
    def username = System.console().readLine 'Enter to send Data'
    println "Sending data.......... "

    for (int i = 0 ; i < 10 ; i++) {
        rannum = Math.abs(new Random().nextInt()) % 20 + 1
        data1 = """
{
"Id" : "i01${i + 1}",
"Name" : "Customer",
"Type" : "JMS",
"Status" : "OK",

"data":
[
{ "name" : "msgRecv", "value" : "1", "type" : "T" },
{ "name": "msgSend", "value" : "5", "type" : "C" },
{ "name": "Errors", "value" : "${rannum}", "type" : "V" },
{ "name": "ErrorXSLT", "value" : "true", "type" : "B" },
{ "name": "LastError", "value" : "NO ERRORS", "type" : "S" }
]
}
"""
        data = data1.bytes
        packet = new DatagramPacket(data, data.length, addr, port)
        socket.send(packet)
        rannum2 = Math.abs(new Random().nextInt()) % 4 + 1
        data2 = """
{
"Id" : "i02${i + 1}",
"Name" : "BankID",
"Type" : "WS",
"Status" : "OK",

"data":
[
{ "name" : "msgRecv", "value" : "${rannum}", "type" : "SR" },
{ "name": "msgSend", "value" : "${rannum2}", "type" : "FR" },
{ "name": "Errors", "value" : "${rannum2}", "type" : "V" },
{ "name": "ErrorXSLT", "value" : "false", "type" : "B" },
{ "name": "LastError", "value" : "Time Out", "type" : "S" }
]
}
"""
        data = data2.bytes
        packet = new DatagramPacket(data, data.length, addr, port)
        socket.send(packet)
    }

    data3 = """
{
"Id" : "i0200",
"Name" : "SRFR",
"Type" : "WS",
"Status" : "OK",

"data":
[
{ "name" : "msgRecv", "value" : "5", "type" : "SR" },
{ "name": "msgSend", "value" : "1000", "type" : "S" },
{ "name": "Errors", "value" : "1", "type" : "S" },
{ "name": "ErrorXSLT", "value" : "false", "type" : "B" },
{ "name": "LastError", "value" : "Time Out", "type" : "S" }
]
}
"""
    data = data3.bytes
    packet = new DatagramPacket(data, data.length, addr, port)
    socket.send(packet)
}
