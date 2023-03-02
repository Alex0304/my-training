package com.ch.train.exception;

import com.ch.train.controller.HelloController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @author DXM-0189
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BusinessException.class)
    public ModelAndView exceptionHandler(BusinessException e) {
        ModelAndView mv = new ModelAndView();
        //设置错误页面
        mv.setViewName("error");
        mv.addObject("msg", e.getMessage());
        logger.error(e.getMessage());
        return mv;
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView exceptionHandler(RuntimeException e) {
        ModelAndView mv = new ModelAndView();
        //设置错误页面
        mv.setViewName("error");
        mv.addObject("msg", "服务器异常");
        logger.error(e.getMessage());
        return mv;
    }
}
