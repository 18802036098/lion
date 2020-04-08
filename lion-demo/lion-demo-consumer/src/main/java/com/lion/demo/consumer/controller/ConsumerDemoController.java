package com.lion.demo.consumer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lion.common.amqp.AmqpSender;
import com.lion.common.base.controller.BaseController;
import com.lion.common.constant.ResponseCode;
import com.lion.common.entity.Result;
import com.lion.common.lock.annotation.Locker;
import com.lion.common.util.DateUtil;
import com.lion.demo.consumer.client.ProviderDemoClient;
import com.lion.demo.consumer.handler.BlockHandler;
import com.lion.demo.consumer.service.IAsyncTaskService;
import com.netflix.ribbon.hystrix.FallbackHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ConsumerDemoController
 * 服务消费者示例代码
 *
 * @author Yanzheng https://github.com/micyo202
 * @date 2019/01/05
 * Copyright 2019 Yanzheng. All rights reserved.
 */
@Api("服务消费者示例")
@RestController
@Slf4j
@RefreshScope
public class ConsumerDemoController extends BaseController {

    /**
     * 自定义配置值
     */
    @Value("${foo}")
    private String foo;

    @Autowired
    private ProviderDemoClient providerDemoClient;

    @ApiOperation("初始化")
    @GetMapping("/init")
    public Result init() {

        String msg = applicationName + " -> port: " + port + ", version: " + version + ", foo: " + foo;

        Result providerInitResult = providerDemoClient.initFromProvider();
        if (providerInitResult.getCode() != ResponseCode.SUCCESS) {
            return Result.failure(msg + ", " + providerInitResult.getMsg());
        } else {
            return Result.success(msg + ", " + providerInitResult.getData());
        }

    }

    @ApiOperation("Feign服务调用，返回Hi文本内容")
    @ApiParam(name = "name", value = "名称（默认lion）", defaultValue = "lion", required = true)
    @GetMapping("/feign/hi")
    public Result feignHi(String name) {
        log.info("Consumer -> 服务消费者 /feign/hi");
        return providerDemoClient.hiFromProvider(name);
    }

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation("Ribbon服务调用，返回Hi文本内容")
    @ApiParam(name = "name", value = "名称（默认lion）", defaultValue = "lion", required = true)
    @SentinelResource(value = "ribbonHi", fallback = "ribbonHiFallback")
    @GetMapping("/ribbon/hi")
    public Result ribbonHi(String name) {

        String method = this.getRequest().getMethod();
        Result result = null;
        if ("GET".equals(method)) {
            Map<String, Object> params = new HashMap<>();
            params.put("name", name);

            String url = "http://lion-demo-provider/hi?name={name}";
            result = restTemplate.getForObject(url, Result.class, params);
        }
        if ("POST".equals(method)) {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("name", name);

            String url = "http://lion-demo-provider/hi";
            result = restTemplate.postForObject(url, params, Result.class);
        }

        log.info("Consumer -> 服务消费者 ribbon 发起 " + method + " 请求结果：" + result);

        return result;
    }

    /**
     * Ribbon服务熔断降级
     */
    public Result ribbonHiFallback(String name) {
        return Result.failure("Ribbon Hi: '" + name + "', fallback sentinel");
    }

    @ApiOperation("Sentinel流量控制")
    @GetMapping("/sentinel/block")
    @SentinelResource(value = "sentinelBlock", blockHandler = "sentinelBlockHandler", blockHandlerClass = BlockHandler.class)
    public Result sentinelBlock() {
        return Result.success("This is sentinel control service flow");
    }

    @ApiOperation("Sentinel服务熔断降级")
    @GetMapping("/sentinel/fallback")
    @SentinelResource(value = "sentinelFallback", fallback = "sentinelFallback", fallbackClass = FallbackHandler.class)
    public Result sentinelFallback() {
        throw new RuntimeException();
        //return Result.failure(500, "This is sentinel control service fallback");
    }

    @ApiOperation("获取用户凭证信息")
    @RequestMapping(value = "/principle", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication) {
        log.info(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());
        log.info(oAuth2Authentication.toString());
        log.info("principal.toString() " + principal.toString());
        log.info("principal.getName() " + principal.getName());
        log.info("authentication: " + authentication.getAuthorities().toString());
        return Result.success(oAuth2Authentication);
    }

    @ApiOperation("角色控制 - 需要拥有admin角色")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getAdmin() {
        return Result.success("当前用户，拥有Admin权限可访问");
    }

    @ApiOperation("角色控制 - 需拥有user角色")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getUser() {
        return Result.success("当前用户，拥有User权限可访问......");
    }

    @ApiOperation("Redisson分布式锁")
    @Locker
    @RequestMapping(value = "/lock", method = {RequestMethod.GET, RequestMethod.POST})
    public Result lock() {
        try {
            log.info("执行锁中业务逻辑");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success("分布式锁方法执行成功");
    }

    @Autowired
    private IAsyncTaskService asyncTaskService;

    @ApiOperation("异步线程执行任务")
    @GetMapping("/async")
    public Result async() {
        asyncTaskService.asyncJob("A");
        asyncTaskService.asyncJob("B");
        return Result.success("异步线程任务调用完成，请查看日志结果");
    }

    @Autowired
    private AmqpSender amqpSender;

    @ApiOperation("AMQP消息发送/接收")
    @RequestMapping(value = "/amqp", method = {RequestMethod.GET, RequestMethod.POST})
    public Result amqp() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("msg", "AMQP发送消息");
        map.put("valid", true);
        map.put("sendTime", DateUtil.getCurrentDateTime());
        Result result = Result.success(map);

        amqpSender.send(result);

        return Result.success("消息发送成功，请查看消息接收日志");
    }

    @ApiOperation("文件上传")
    @ApiParam(name = "file", value = "附件内容")
    @PostMapping("/upload")
    public Result upload(HttpServletRequest request) {
        List<String> list = fileUpload(request);
        return Result.success(list);
    }

    @ApiOperation("文件下载")
    @ApiParam(name = "fileName", value = "文件名称", required = true)
    @GetMapping("/download/{fileName}")
    public Result download(@PathVariable String fileName, HttpServletResponse response) {
        boolean result = fileDownload(fileName, response);
        return result ? Result.success("文件下载成功") : Result.failure("文件下载失败");
    }

}