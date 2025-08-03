This app is the same as the one for building 3 KRAFT Brokers/controllers. 
What it does provide 
- Integration with JMX inbuilt so that multiple docker images are not required
- Custom docker images - For now these need to be built **by hand**
- An external network has to be created by hand  

docker network create --subnet=172.80.0.0/16 kafka-demo

e.g. docker build container_images\kafka -t customkafka -f container_images\kafka\Dockerfile 
e.g. docker build container_images\provectus -t customkafka -f container_images\provectus\Dockerfile 
	

Sample server.properties and log4j.properties are also provided so that these can be mounted to /kafka/config directories 