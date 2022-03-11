package com.genius.cloud.mq.producer;

import com.genius.cloud.mq.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducerOrder {

    // 生产者
    private final DefaultMQProducer defaultMQProducer;

    public ProducerOrder() {
        // 生产者
        defaultMQProducer = new DefaultMQProducer(JmsConfig.PRODUCER_GROUP);
        // 不开启vip通道 开通口端口会减2
        defaultMQProducer.setVipChannelEnabled(false);
        // 绑定name server
        defaultMQProducer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        start();
    }

    public SendResult send(Message message) {
        try {
            return getDefaultMQProducer().send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            log.error("send message error: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.defaultMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public DefaultMQProducer getDefaultMQProducer() {
        return this.defaultMQProducer;
    }

    /**
     * 一般在应用上下文，使用上下文监听器，进行关闭
     */
    public void shutdown() {
        this.defaultMQProducer.shutdown();
    }

}
