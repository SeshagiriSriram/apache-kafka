## Getting started with kafka-getting-started

This folder contains the following folders - each representing a single use case

* 00.SingleZooKafka
* 01.SingleKafkaKraft
* 02.ZookeeperKafka
* 03.QuorumKafkaKraft
* 04.QuorumKafkaKraftCustomDocker

We do provide
* log4j.properties
* server.properties 

These can be mounted in your docker images to have "custom" behaviours. 
The location of these files unfortunately depends on the base docker image being used. 

A rough guide (refer to actual docker image documentation) is as follows: 


* bitnami/kafka
    * logs: /opt/bitnami/kafka/logs
	* bin: /opt/bitnami/kafka/bin 
	* config: /opt/bitnami/kafka/config[/kraft]
* confluent: 
	* logs: <<by default logger is not to a file>> 
	* bin: /bin 
	* config: /etc/kafka/[/kraft]
	* secrets: /etc/kafka/secrets 

### 00.SingleZooKafka
Cluster with Single Kafka with Single Zookeper 

### 01.SingleKafkaKraft
Cluster with Single Kafka with No Zookeeper - just running in KRAFT mode. 

### 02.ZookeeperKafka
A cluster with 3 Zookepers and 3 Kafka Brokers/Controllers. Play around with bringing individual components to see the effect of leadership elections and Quorom 

### 03.QuorumKafkaKraft
Same as above but no Zookeepers - Runining in KAFKA mode. Play around with bringing individual components to see the effect of leadership elections and Quorom 

### 04.QuorumKafkaKraftCustomDocker
Same as above - just using our own custom Docker Image