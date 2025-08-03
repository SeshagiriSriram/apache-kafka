package com.sriram.flink;

import com.sriram.flink.model.Backup;
import com.sriram.flink.model.InputMessage;
import com.sriram.flink.operator.BackupAggregator;
import com.sriram.flink.operator.InputMessageTimestampAssigner;
import com.sriram.flink.operator.WordsCapitalizer;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import static com.sriram.flink.connector.Consumers.createInputMessageConsumer;
import static com.sriram.flink.connector.Consumers.createStringConsumerForTopic;
import static com.sriram.flink.connector.Producers.createBackupProducer;
import static com.sriram.flink.connector.Producers.createStringProducer;

public class FlinkDataPipeline {

    public static void capitalize() throws Exception {
        String inputTopic = "flink_input";
        String outputTopic = "flink_output";
        String consumerGroup = "sriram";
        String address = "localhost:9092";

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        FlinkKafkaConsumer<String> flinkKafkaConsumer = createStringConsumerForTopic(inputTopic, address, consumerGroup);
        flinkKafkaConsumer.setStartFromEarliest();

        DataStream<String> stringInputStream = environment.addSource(flinkKafkaConsumer);

        FlinkKafkaProducer<String> flinkKafkaProducer = createStringProducer(outputTopic, address);

        stringInputStream.map(new WordsCapitalizer())
            .addSink(flinkKafkaProducer);

        environment.execute();
    }

    public static void createBackup() throws Exception {
        String inputTopic = "flink_input";
        String outputTopic = "flink_output";
        String consumerGroup = "sriram";
        String kafkaAddress = "localhost:9092";

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        FlinkKafkaConsumer<InputMessage> flinkKafkaConsumer = createInputMessageConsumer(inputTopic, kafkaAddress, consumerGroup);
        flinkKafkaConsumer.setStartFromEarliest();

        flinkKafkaConsumer.assignTimestampsAndWatermarks(new InputMessageTimestampAssigner());
        FlinkKafkaProducer<Backup> flinkKafkaProducer = createBackupProducer(outputTopic, kafkaAddress);

        DataStream<InputMessage> inputMessagesStream = environment.addSource(flinkKafkaConsumer);

        inputMessagesStream.timeWindowAll(Time.hours(24))
            .aggregate(new BackupAggregator())
            .addSink(flinkKafkaProducer);

        environment.execute();
    }

    public static void main(String[] args) throws Exception {
        createBackup();
    }

}
