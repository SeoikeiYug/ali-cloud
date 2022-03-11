package com.genius.cloud.mq.demo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

public class Producer_Orderly {

    public static void main(String[] args) throws Exception {
        // 1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("genius_producer_orderly_group");
        // 2.指定Nameserver地址
        producer.setNamesrvAddr("106.12.134.254:9876");
        // 3.启动producer
        producer.start();
        System.out.println("生产者启动");
        for (int i = 0; i < 20; i++) {
            // 4.创建消息对象，指定主题Topic、Tag和消息体
            /*
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("Topic_order_genius", "Tag_order_genius",
                    ("Hello genius，" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 5.发送有序消息
            SendResult send = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer index = (Integer) arg;
                    return mqs.get(index);
                }
            }, 1);
            System.out.println("发送结果：" + send);
            // 线程睡1秒
        }

        // 6.关闭生产者producer
        producer.shutdown();
        System.out.println("生产者关闭");
    }
}

