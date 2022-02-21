package com.genius.cloud.service;


import com.genius.cloud.common.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "ali-cloud-seata-account")
public interface AccountService {

    @PostMapping(value = "/account/decrease")
    CommonResult<Object> decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);

}
