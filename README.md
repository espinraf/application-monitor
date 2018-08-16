# Application Monitor

(Started refactoring with spring-boot)

Monitor your applications easy using Application Monitor dash-board.
Send data to Application Monitor via UDP Server in JSON format described below.

![Application Monitor](docs/Application_Monitor.png)

## Requisites

* Gradle 2.3 +
* Java 1.7+
* Browser with HTML5 support

## Servers Ports

* UDP Server, default port 9090
* HTTP Server, default port 8080

## JSON Format

    {
    //MANDATORY FROM HERE
    "Id" : "i001",
    "Name" : "Customer",
    "Type" : "JMS",
    "Status" : "OK",

    //DATA TO MONITOR "OPTIONAL" FROM HERE
    "data":
    [
    { "name" : "msgRecv", "value" : "1", "type" : "T" },
    { "name": "msgSend", "value" : "5", "type" : "C" , "ttl" : "1h"},
    { "name": "Errors", "value" : "${rannum}", "type" : "V" },
    { "name": "ErrorXSLT", "value" : "true", "type" : "B" },
    { "name": "LastError", "value" : "NO ERRORS", "type" : "S" },
    { "name" : "msgRecv", "value" : "11", "type" : "SR" },
    { "name": "msgSend", "value" : "2", "type" : "FR" }
    ]
    }



Type | Description
-----|-------------
C | Counter, add ttl : X h/m/s hour, min or sec to reset the counter.
V | Value
T | TPS
S | String
B | Boolean
FR | Failed Rate, SUM(value)/n number of sent values.
SR | Success Rate, SUM(value)/n number of sent values.




## Heartbeat JSON Format to register application.

    {
    //MANDATORY
    "AppId" : "app01",
    "AppName" : "BankID Integration",
    // Seconds Time To Wait
    "ttw" : "60"

    }

## Heartbeat JSON Format to be sent to Web page.

    {
    "AppId" : "app01",
    "AppName" : "BankID Integration",
    "Status : "OK" / "NOK"    
    }

## Run from Gradle

### With default ports

```
gradle run
```
### Overriding ports

```
$ gradle run -DudpServer.port=8080
```

**To change HTTP port, update src/main/resources/application.properties**

## Compile Spring Boot Fat jar

```
$ gradle clean build
```

## Run Spring Boot Fat jar

### With default ports

```
$ java -jar build/libs/application-monitor-1.0.jar
```

### Overriding ports

```

 $ java -DudpServer.port=8080  -jar build/libs/application-monitor-1.0.jar

```

 ## Access Web page

 http://localhost:8080/monitor/index.html 

 ## Send test data

```
 $ cd TestClient

 $ groovy UDPClientHB.groovy
```
