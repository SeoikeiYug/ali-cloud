package com.genius.cloud.service.impl;

import com.genius.cloud.dao.AccountDao;
import com.genius.cloud.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    /**
     * 扣减账户余额
     */
    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("------->account-service中扣减账户余额开始");
        accountDao.decrease(userId, money);
        log.info("------->account-service中扣减账户余额结束");
    }
}
