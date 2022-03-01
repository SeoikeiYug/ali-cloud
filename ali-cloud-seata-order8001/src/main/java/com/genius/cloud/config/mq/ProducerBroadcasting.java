package com.genius.cloud.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ProducerBroadcasting {

    public static void main(String[] args) throws Exception {
        // 1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("genius_producer_broadcasting_group");
        // 2.指定Nameserver地址
        producer.setNamesrvAddr("13.213.65.160:9876");
        // 3.启动producer
        producer.start();
        System.out.println("生产者启动");
        List<Message> msgs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            // 4.创建消息对象，指定主题Topic、Tag和消息体
            /*
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("Topic_broadcasting_genius", "Tag_broadcasting_genius", ("Hello genius，" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            msgs.add(msg);
        }

        // 5.发送消息
        SendResult result = producer.send(msgs);
        // 发送状态
        SendStatus status = result.getSendStatus();

        log.info("发送结果:" + status);

        // 线程睡1秒
        TimeUnit.SECONDS.sleep(1);

        // 6.关闭生产者producer
        producer.shutdown();
        System.out.println("生产者关闭");
    }
}

