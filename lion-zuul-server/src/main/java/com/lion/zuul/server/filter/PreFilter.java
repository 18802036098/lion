package com.lion.zuul.server.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * PreFilter
 * 前置拦截器
 *
 * @author Yanzheng 严正
 * @date 2019/01/02
 * Copyright 2019 Yanzheng. All rights reserved.
 */
//@Component
public class PreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        /**
         * pre：路由之前
         * routing：路由之时
         * post： 路由之后
         * error：发送错误调用
         */
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 5; // 过滤器执行顺序，数字越小，优先级越高，越靠前,这里设置为 0
    }

    @Override
    public boolean shouldFilter() {
        return true;    // 返回 true，拦截所有 URL
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            System.out.println("进入lion-zuul-server服务，执行PreFilter，Token为空！");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());

            try {
                Map<String, Object> map = new HashMap<>();
                map.put("status", "401");
                map.put("msg", "token为空，无权访问！");
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(map);
                requestContext.getResponse().setContentType("application/json;charset=UTF-8");
                requestContext.setResponseBody(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        System.out.println("进入lion-zuul-server服务，执行PreFilter，Token is OK!");
        return null;
    }
}
