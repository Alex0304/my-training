package com.ch.train.controller;

import com.ch.train.entity.Page;
import com.ch.train.entity.Product;
import com.ch.train.form.ProductQueryPageForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenhuan
 */
@Controller
@RequestMapping("/xss")
public class XssController {



    @RequestMapping("/add")
    public String add() {
        return "xssAdd";
    }

    @RequestMapping("/toAdd")
    public String queryByPage(String str, HttpServletRequest request) {
        request.setAttribute("content", str);
        return "xss";
    }
}
