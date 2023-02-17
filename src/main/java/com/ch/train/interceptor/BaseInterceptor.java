package com.ch.train.interceptor;

import com.ch.train.controller.HelloController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author DXM-0189
 *
 * 基础拦截器，拦截controller 层接口方法，打印日志
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {


    private static Log logger = LogFactory.getLog(BaseInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            logger.info("-----------------------开始计时:"+System.currentTimeMillis()+"-------------------------------------");
            HandlerMethod method = (HandlerMethod)handler;
            logger.info("Controller:"+method.getBeanType().getName());
            logger.info("Method:"+method.getMethod().getName());
            logger.info("Params:"+request.getQueryString());
            logger.info("Uri:"+request.getRequestURI());
        }
        return true;
    }


}
