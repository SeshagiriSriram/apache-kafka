package com.sriram.kafka.commitoffset;

import com.sriram.kafka.commitoffset.config.KafkaConfigProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

public class AsyncCommit {

    public static void main(String[] args) {

        KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(KafkaConfigProperties.getProperties());
        consumer.subscribe(KafkaConfigProperties.getTopic());
        ConsumerRecords<Long, String> messages = consumer.poll(Duration.ofSeconds(10));
        for (ConsumerRecord<Long, String> message : messages) {
            // processed message
            consumer.commitAsync();
        }
    }
}
