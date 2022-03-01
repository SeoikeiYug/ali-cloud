package com.genius.cloud.config.mq;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
public class ProducerBatch {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("genius_producer_batch_group");
        // 2.指定Nameserver地址
        producer.setNamesrvAddr("13.213.65.160:9876");
        // 3.设置过期时间
        producer.setSendMsgTimeout(10000);
        // 4.设置不走vip通道
        producer.setVipChannelEnabled(false);
        // 5.启动producer
        producer.start();
        log.info("生产者启动");
        // 6.创建消息对象，指定主题Topic、Tag和消息体
        /*
         * 参数一：消息主题Topic
         * 参数二：消息Tag
         * 参数三：消息内容
         */
        List<Message> msgs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Message msg = new Message("Topic_batch_genius", "Tag_batch_genius", ("Hello genius，这是批量消息" + i).getBytes());
            msgs.add(msg);
        }

        // 7.发送消息
        SendResult result = producer.send(msgs);
        // 8.发送状态
        SendStatus status = result.getSendStatus();

        System.out.println("发送结果:" + status);

        // 9.线程睡1秒
        TimeUnit.SECONDS.sleep(1);

        // 10.关闭生产者producer
        producer.shutdown();
       log.info("生产者关闭");
    }

}
