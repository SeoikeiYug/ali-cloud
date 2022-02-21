package com.genius.cloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com/genius/cloud/dao"})
public class MyBatisConfig {
}
