package com.genius.cloud.service;

import com.genius.cloud.common.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ali-cloud-seata-storage")
public interface StorageService {

    @PostMapping(value = "/storage/decrease")
    CommonResult<Object> decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);

}
