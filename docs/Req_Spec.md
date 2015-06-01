#APPLICATION MONITOR

##Requirements Specification

### Functional Requrements
1) It shall be possible to use it with all applications implemented in Java, Groovy, Vertx.

2) The applications will send data to the monitor using JSON Fortmat

3) The data shall be presented using a Web page.

4) The web page shall show the status of the application with Colors

	-  Green: OK
	-  Yellow: Warning, the application has thrown Errors.
	-  Red: Application unavailable or unstable
	-  <User defined>: User can define it own color with its own meaning.

5) All the diplayed data about an application will be User Defined.

	Example:
	
	{
		"Id" : "i001", # Mandatory
		"Name" : "Customer", # Mandatory
		"Type" : "JMS" # Mandatory
		"Status" : "OK", # Mandatory 
	  
		"data": #User defined
		[
			{ "name" : "msgRecv", "value" : "1", "type" : "C" },  
			{ "name": "msgSend", "value" : "5", "type" : "C" },
			{ "name": "Errors", "value" : "0", "type" : "C" },
			{ "name": "ErrorXSLT", "value" : "1", "type" : "B" },
			{ "name": "LastError", "value" : "0", "type" : "S" }
		]
	}

6) In the web page the user shall be able to define threshold per parameter.

	i.e. Param: Error Threshold Min: 5 Max: 10
	
	Error < 5 - Green
	Error between 5 - 10 - Yellow
	Error > 5 Red

* Type "C" Counter.

Counter - I shall be possible to reset it.

* Type "V" Value

Value must have a range Min and Max, in this case the status will be Green if the Value is in the Range and Red if is below or above the range.

Also Value shall show the average between the last Value/Average and the new Value.

new Average = (last Value/Average + new Value) / 2

* Type String "S"

In this case shall be posible to enter a string which can be contained in the String. in the case that is reported, then the status should be RED


	
7) In the web page the application shall be shown using a Circle or User Image in PNG format.

8) When the user clicks on a application, it shall be shown a window with the User defined parameters


###Non-functional Requirements

1) Implementation: Java/Groovy 

2) Web page, Javascript, css, jquery, d3

3) Storege, embedded Neo4J

