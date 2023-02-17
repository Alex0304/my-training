package com.ch.train.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@Controller
public class HelloController {

    private static Log logger = LogFactory.getLog(HelloController.class);

    @RequestMapping("/hello")
    public String sayHello(){
        logger.info("hjjsjs");
        return "index";
    }

    @RequestMapping("/jstl")
    public String test(){
        return "headJstl";
    }

    @RequestMapping("/headCssJs")
    public String test11(){
        return "headCssJs";
    }


    @RequestMapping("/demo")
    public String demo(Model model) {
        model.addAttribute("msg", "Spring Boot 集成 JSP 模板");
        return "demo";
    }

}


