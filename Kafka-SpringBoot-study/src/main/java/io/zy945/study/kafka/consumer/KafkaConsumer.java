package io.zy945.study.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {


    /**
     * 配置topic和分区,可以配置多个
     * topic-- 需要监听的 Topic 的名称，
     * partitions – 需要监听 Topic 的分区 id。
     * partitionOffsets – 可以设置从某个偏移量开始监听，@PartitionOffset：partition – 分区 Id，非数组，initialOffset – 初始偏移量。
     */
    @KafkaListener(topicPartitions = {
            @TopicPartition(
                    topic = "topic.test", partitions = {"0", "2"},
                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "4"))
    })
    public void onMessage1(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("--------------这是分区0、1、3--------------");
        System.out.println("监听的topic：" + record.topic());
        System.out.println("监听的partitionL: " + record.partition());
        System.out.println("收到的消息的key: " + record.key());
        System.out.println("收到的消息的value: " + record.value());
    }
}