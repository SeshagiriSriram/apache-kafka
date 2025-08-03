# Getting Started with Kafka

This repos is meant to get started with Kafka. Is is continually a work in progress as changes will be reflected. 

It consist of the following 5 "main" folders. 

* **kafka-getting-started** -Basic setup and starting of Kafka
* **kafka-sample-code** - Some code related to 
    * CommitOffsets
    * Headers
    * Sample Consumers
    * ExactlyOnce Semantics
    * Messages 
    * Serialization and Deserialization
* **kafka-testing** - Testing with 
    * Mock Objects
    * Test Containers
    * Embedded Kafka - (WIP)
    * Mockito - (WIP)
    * Load Testing Kafka with JMeter and KLOADGEN
* **kafka-labs** - Set of apps related to 
    * Mirror Maker2 
    * Debezium and Kafka Connect
    * Migrate from Zookeeper to KRAFT 
    * ... 
* **kafka-scripts** - WIP - Largely consisting of scripts related to Cert Managements

(WIP) - Additional folders may be added to include documents and Resource Links 

## Required SW

* **Docker and Docker Compose** - How you set it up is your call. Pretty nuch I install docker on **WSL Ubuntu** and set up Windows to talk to this docker. That's just ne - you are free to use Docker Desktop if that's more convenient to you.
    * **NB** - If you are doing the above, you will need to set up testcontainers to run the test cases as Docker is remote :-) 
* **JDK** - I use OpenJDK 21 - Cursorily JDK 17 should work - but not fully tested yet

### Notes and Caveats

* Each folder has its own README.md that provides additional information. 
* It's strongly suggested to clean up after "Playing" around with each folder.
* Be aware that each "use-case" folder use different docker images. 
    * In specific, BITNAMI and CONFLUENT images are used. 
    * We do provide an use case for creating custom images
* Many of these images enable use of Kafka in both Zookeper and KRAFT modes. 
    * ***The use of latest tags in most cases can be used only in KRAFT mode***

### TODO (not Ranked by any priority - No ETA)

* Clean up README.md files 
* Clean up .gitignore and .dockerignore files
* Standardize on docker images and versions across applications 
* Ensure testability between JDK 17 to 21
* Extend support for JDK 24+ 
* Standardize on Builds in docker-compose yaml files 
* Better parameterization of Dockerfile and docker-compose files 
