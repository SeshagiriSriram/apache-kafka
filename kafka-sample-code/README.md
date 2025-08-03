## Apache Kafka Sample Code 

This module contains articles about Apache Kafka. Currently we offer code samples for 

    * CommitOffsets
    * Headers
    * Sample Consumers
    * ExactlyOnce Semantics
    * Messages 
    * Serialization and Deserialization

### Building the project
You can build the project from the command line using: *mvn clean install*, or in an IDE.

### Notes and Credits
This is based on Baeldungs course on Java with Kafka. A few changes
- I had to run as *mvn clean install -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true*
- confluent kafka latest image fails. These tests have been ignored as well as many live tests. Adapt as required

### ROADMAP 

* Add support for DLQ and Retries
* Add code 
    * Kafka Streams
    * KSQL 
    * Kafka Connect
    * Storm/Flink/Stimzi/Big Data.... (let me know)