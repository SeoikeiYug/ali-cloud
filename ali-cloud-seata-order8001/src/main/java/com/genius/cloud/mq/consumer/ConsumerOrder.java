package com.genius.cloud.mq.consumer;

import com.genius.cloud.mq.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ConsumerOrder {

    /**
     * 通过构造函数 实例化对象
     */
    public ConsumerOrder() throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(JmsConfig.CONSUMER_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        // 消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 订阅主题和 标签（ * 代表所有标签)下信息
        defaultMQPushConsumer.subscribe(JmsConfig.TOPIC_ORDER, "*");
        // 注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
            // messages中只收集同一个topic，同一个tag，并且key相同的message
            // 会把不同的消息分别放置到不同的队列中
            for (Message message : messages) {
                //消费者获取消息 这里只输出 不做后面逻辑处理
                String body = new String(message.getBody(), StandardCharsets.UTF_8);
                log.info("Consumer-获取消息-主题topic为={}, 消费消息为={}", message.getTopic(), body);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        defaultMQPushConsumer.start();
        log.info("消费者启动成功");
    }

}
