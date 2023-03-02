package com.ch.train.controller;

import com.ch.train.entity.Page;
import com.ch.train.entity.Product;
import com.ch.train.form.ProductQueryPageForm;
import com.ch.train.form.ProductSaveForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
