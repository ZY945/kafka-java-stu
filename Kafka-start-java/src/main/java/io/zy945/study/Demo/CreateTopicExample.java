package io.zy945.study.Demo;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import java.util.Collections;
import java.util.Properties;

public class CreateTopicExample {
    public static void main(String[] args) {
        // Kafka 服务器地址
        String bootstrapServers = "ip:9092";
        // 创建 AdminClient 配置
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(properties)) {
            // 创建一个名为 "test" 的主题
            NewTopic newTopic = new NewTopic("test", 1, (short) 1);
            adminClient.createTopics(Collections.singleton(newTopic)).all().get();
            System.out.println("Topic created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}