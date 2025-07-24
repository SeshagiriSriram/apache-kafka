@echo off 
echo Creating network
docker network create --subnet=172.80.0.0/16 kafka-demo
echo Creating Custom KAFKA
docker build container_images\kafka -t customkafka -f container_images\kafka\Dockerfile 
echo Creating Custom Provectus
docker build container_images\provectus -t customprovectus -f container_images\provectus\Dockerfile 