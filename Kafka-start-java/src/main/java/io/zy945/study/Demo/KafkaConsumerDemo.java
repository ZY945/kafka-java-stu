package io.zy945.study.Demo;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerDemo {
    public static void main(String[] args) {
        String bootstrapServers = "ip:9092";
        String topic = "my-topic";
        String groupId = "my-group";

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", groupId);
//        props.setProperty("enable.auto.commit", "true");//设置为自动提交
//        props.setProperty("auto.commit.interval.ms", "1000");//自动提交间隔时间

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer(props)) {
            //Collections.singletonList被限定只被分配一个内存空间，也就是只能存放一个元素的内容。，不会浪费空间
            consumer.subscribe(Collections.singletonList(topic));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));//kafka实质是以pull的方式去kafka拉取消息消费
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Received message: topic = " + record.topic() +
                            ", partition = " + record.partition() +
                            ", offset = " + record.offset() +
                            ", key = " + record.key() +
                            ", value = " + record.value());
                }
                // 如果成功，手动通知offset提交
//                consumer.commitAsync();
            }
        }
    }
}