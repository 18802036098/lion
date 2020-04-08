package com.lion.demo.consumer.client;

import com.lion.common.entity.Result;
import com.lion.demo.consumer.client.fallback.ProviderDemoClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ProviderDemoClient
 * 服务调用类
 *
 * @author Yanzheng https://github.com/micyo202
 * @date 2019/01/05
 * Copyright 2019 Yanzheng. All rights reserved.
 */
@FeignClient(value = "lion-demo-provider", fallback = ProviderDemoClientFallback.class)
public interface ProviderDemoClient {

    @GetMapping(value = "/init")
    Result initFromProvider();

    @GetMapping(value = "/hi")
    Result hiFromProvider(@RequestParam(value = "name") String name);

    @RequestMapping("/temp/product/deduct")
    Result deductFromProvider(@RequestParam("productCode") String productCode, @RequestParam("count") int count);
}
