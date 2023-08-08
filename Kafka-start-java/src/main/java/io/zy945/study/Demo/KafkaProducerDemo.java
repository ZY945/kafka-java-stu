package io.zy945.study.Demo;


import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class KafkaProducerDemo {
    public static void main(String[] args) {
        String bootstrapServers = "ip:9092";
        String topic = "my-topic";

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            String key = "key-" + i;
            String value = "value-" + i;
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("Message sent successfully: topic = " + metadata.topic() +
                                ", partition = " + metadata.partition() +
                                ", offset = " + metadata.offset() +
                                ", key = " + key +
                                ", value = " + value);
                    } else {
                        System.out.println("Failed to send message: " + exception.getMessage());
                    }
                }
            });
        }

        producer.close();
    }
}