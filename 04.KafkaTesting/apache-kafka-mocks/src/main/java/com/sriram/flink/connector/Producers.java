package com.sriram.flink.connector;

import com.sriram.flink.model.Backup;
import com.sriram.flink.schema.BackupSerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

public class Producers {

    public static FlinkKafkaProducer<String> createStringProducer(String topic, String kafkaAddress) {
        return new FlinkKafkaProducer<>(kafkaAddress, topic, new SimpleStringSchema());
    }

    public static FlinkKafkaProducer<Backup> createBackupProducer(String topic, String kafkaAddress) {
        return new FlinkKafkaProducer<Backup>(kafkaAddress, topic, new BackupSerializationSchema());
    }
}
