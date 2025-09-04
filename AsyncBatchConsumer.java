import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

public class AsyncBatchConsumer {

    private static final String TOPIC = "my-topic";
    private static final String DLQ_TOPIC = "my-topic-dlq";
    private static final int MAX_RETRIES = 3;

    private final KafkaConsumer<String, String> consumer;
    private final KafkaProducer<String, String> producer;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public AsyncBatchConsumer() {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "batch-consumer-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(TOPIC));

        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<>(producerProps);
    }

    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();

            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (ConsumerRecord<String, String> record : records) {
                CompletableFuture<Void> future = processWithRetry(record, MAX_RETRIES)
                    .thenAccept(v -> {
                        TopicPartition tp = new TopicPartition(record.topic(), record.partition());
                        offsetsToCommit.put(tp, new OffsetAndMetadata(record.offset() + 1));
                    })
                    .exceptionally(ex -> {
                        sendToDLQ(record, ex);
                        return null;
                    });

                futures.add(future);
            }

            // Wait for all processing to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            // Commit only successfully processed offsets
            if (!offsetsToCommit.isEmpty()) {
                consumer.commitSync(offsetsToCommit);
            }
        }
    }

    private CompletableFuture<Void> processWithRetry(ConsumerRecord<String, String> record, int retriesLeft) {
        return CompletableFuture.runAsync(() -> {
            try {
                processRecord(record);
            } catch (Exception e) {
                if (retriesLeft > 0) {
                    throw new CompletionException(processWithRetry(record, retriesLeft - 1).join());
                } else {
                    throw new CompletionException(e);
                }
            }
        }, executor);
    }

    private void processRecord(ConsumerRecord<String, String> record) throws Exception {
        // Simulate processing logic
        if (record.value().contains("fail")) {
            throw new RuntimeException("Processing failed for record: " + record.value());
        }
        System.out.println("Processed: " + record.value());
    }

    private void sendToDLQ(ConsumerRecord<String, String> record, Throwable ex) {
        ProducerRecord<String, String> dlqRecord = new ProducerRecord<>(DLQ_TOPIC, record.key(), record.value());
        producer.send(dlqRecord, (metadata, exception) -> {
            if (exception != null) {
                System.err.println("Failed to send to DLQ: " + exception.getMessage());
            } else {
                System.out.println("Sent to DLQ: " + record.value());
            }
        });
    }

    public static void main(String[] args) {
        new AsyncBatchConsumer().run();
    }
}