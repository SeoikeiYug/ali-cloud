package com.genius.cloud.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ProducerTransaction {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        // 1.创建消息生产者PRODUCER, 并指定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("genius_transaction_group");
        // 2.指定NameServer地址
        producer.setNamesrvAddr("106.12.134.254:9876");
        // 3.添加事务监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 完成本地事务逻辑
             * @param message /
             * @param o       /
             * @return /
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                log.info("正在执行本地事务!");
                if (StringUtils.equals("TAGA", message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equals("TAGB", message.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (StringUtils.equals("TAGC", message.getTags())) {
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.UNKNOW;
            }

            /**
             * 消息回查
             * @param messageExt /
             * @return /
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                log.info("消息的Tag: " + messageExt.getTags());
                return LocalTransactionState.UNKNOW;
            }
        });
        // 4.设置线程池
        producer.setExecutorService(new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2000), r -> {
                    Thread thread = new Thread(r);
                    thread.setName("client-transaction-msg-check-thread");
                    return thread;
                })
        );
        // 5.启动producer
        producer.start();
        log.info("生产者启动!");

        String[] tags = {"TAGC", "TAGB", "TAGA"};
        for (int i = 0; i < 3; i++) {
            // 6.创建消息对象，指定主题Topic、Tag和消息体
            /*
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("TransactionTopic", tags[i], ("Hello genius" + i).getBytes());
            // 7.发送消息
            SendResult result = producer.sendMessageInTransaction(msg, "hello_genius_transaction");
            // 8.发送状态
            SendStatus status = result.getSendStatus();

            log.info("发送结果:" + result);
            log.info("发送结果状态:" + status);
            // 9.线程睡2秒
            TimeUnit.SECONDS.sleep(60 * 17);
        }
    }

}
