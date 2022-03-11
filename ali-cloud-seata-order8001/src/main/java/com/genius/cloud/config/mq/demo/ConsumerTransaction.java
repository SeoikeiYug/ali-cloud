package com.genius.cloud.config.mq.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

@Slf4j
public class ConsumerTransaction {

    public static void main(String[] args) throws MQClientException {
        // 1.创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("genius_transaction_group");
        // 2.指定Nameserver地址
        consumer.setNamesrvAddr("106.12.134.254:9876");
        // 3.订阅主题Topic和Tag
        consumer.subscribe("TransactionTopic", "*");
        // 4.设置回调函数，处理消息
        // 接受消息内容
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                log.info("consumeThread=" + Thread.currentThread().getName() + "," + new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 5.启动消费者consumer
        consumer.start();
        log.info("消费者启动");
    }

}
