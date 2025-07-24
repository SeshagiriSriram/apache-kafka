## Apache Kafka

This module contains articles about Apache Kafka.

##### Building the project
You can build the project from the command line using: *mvn clean install*, or in an IDE.

##### Notes and Credits
This is based on Baeldungs course on Java with Kafka. A few changes
- I had to run as *mvn clean install -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true*
- confluent kafka latest image fails. These tests have been ignored as well as many live tests. Adapt as required

