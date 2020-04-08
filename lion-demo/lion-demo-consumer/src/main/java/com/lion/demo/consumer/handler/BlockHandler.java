package com.lion.demo.consumer.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lion.common.constant.ResponseCode;
import com.lion.common.entity.Result;

/**
 * BlockHandler
 * sentinel流量控制自定义方法
 *
 * @author Yanzheng https://github.com/micyo202
 * @date 2019/08/19
 * Copyright 2019 Yanzheng. All rights reserved.
 */
public class BlockHandler {

    public static Result sentinelBlockHandler(BlockException e) {
        e.printStackTrace();
        return Result.failure(ResponseCode.TOO_MANY_REQUESTS, "限流控制（Sentinel is blocked...）");
    }

}