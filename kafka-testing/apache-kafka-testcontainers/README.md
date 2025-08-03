## Apache Kafka Testing with  Mock Objects

This is a maven project that tests a Kafka application using Mock Objects 

### Building the project
You can build the project from the command line using: *mvn clean install*, or in an IDE.
NB: The above command also runs the tests for same. 

## CAVEATS
* If testing with Remote Docker,  
	* Ensure DOCKER_HOST is properly  set 
	* Make Sure Certs are properly installed. 
		* In the absense  of this, use HTTP  instead of  HTTPS ( WHICH IS  **NEVER** RECOMMENDED) 

### TODO/ROADMAP 
* Build prrofile s for running Unit and Integration Tests separately 
* Support for JDK 17 to JDK21 to be re-tested 
 * Add Support for JDK 24 
 * Add Support for GenericContainers 