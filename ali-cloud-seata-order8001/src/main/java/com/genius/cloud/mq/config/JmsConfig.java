package com.genius.cloud.mq.config;

public class JmsConfig {

    /**
     * Name Server 地址，因为是集群部署 所以有多个用 分号 隔开
     */
    public static final String NAME_SERVER = "106.12.134.254:9876";
    /**
     * 生产者组名
     */
    public static final String PRODUCER_GROUP = "producer_group";
    /**
     * 消费者组名
     */
    public static final String CONSUMER_GROUP = "consumer_group";
    /**
     * 主题名称 主题一般是服务器设置好 而不能在代码里去新建topic（ 如果没有创建好，生产者往该主题发送消息 会报找不到topic错误）
     */
    public static final String TOPIC_ORDER = "topic_order";

}
