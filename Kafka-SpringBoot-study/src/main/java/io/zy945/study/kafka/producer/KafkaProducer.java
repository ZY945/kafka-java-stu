package io.zy945.study.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@Slf4j
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 最普通发送
     */
    @GetMapping("/kafka/test/1")
    public void sendMessage1() {
        kafkaTemplate.send("topic.test", "普通消息指定-topic-key-value", "message data");
        System.out.println("-------发送消息成功-------");
    }

    /**
     * 指定分片发送
     */
    @GetMapping("/kafka/test/2")
    public void sendMessage2() {
        kafkaTemplate.send("topic.test", 2, "带分片消息指定-topic-partition-key-value", "message data");
        System.out.println("-------发送分区为2消息成功-------");
    }

    /**
     * 指定分片发送
     * 附带时间
     */
    @GetMapping("/kafka/test/3")
    public void sendMessage3() {
        kafkaTemplate.send("topic.test", 3, System.currentTimeMillis(), "带分片和时间消息指定-topic-partition-key-value", "message data");
        System.out.println("-------发送分区为1消息成功-------");
    }

    /**
     * get方法来判断消息发送时间是否过长
     * 超出时间会抛异常,但是消息仍然会发送
     */
    @GetMapping("/kafka/test/limitTime")
    public void sendMessage4() throws ExecutionException, InterruptedException, TimeoutException {
        // get方法可以判断消息发送情况,指定时间未发生,会抛异常,但是消息还是会发送
        kafkaTemplate.send("topic.test", "慢查询sql", "get方法判断-1ms内未发送").get(1, TimeUnit.MICROSECONDS);
        System.out.println("-------发送消息成功-------");
    }

    /**
     * springboot3的callBack
     */
    @GetMapping("/kafka/test/callBack")
    public void sendMessage5() {
        String data = "message data";
        CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send("topic.test", "callBack", data);
        System.out.println("-------发送消息成功-------");
        //SpringBoot3的写法

        //执行成功回调
        completableFuture.thenAccept(result -> {
            // debug会有心跳机制好像,一直在打日志
            // 开启debug，需要修改日志级别
//            log.debug("发送成功:{}", data);
            log.info("发送成功:{}", data);
        });
        // 执行失败回调
        completableFuture.exceptionally(e -> {
            log.error("发送失败:{}", data, e);
            return null;
        });
    }
}