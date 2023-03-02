package com.ch.train.controller;


import com.ch.train.entity.Page;
import com.ch.train.entity.Product;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.IdForm;
import com.ch.train.form.ProductQueryPageForm;
import com.ch.train.form.ProductSaveForm;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhuan
 */
@Controller
@RequestMapping("/product")
public class ProductManageController implements InitializingBean {

    private static final List<Product> productList = new ArrayList<>();




    @RequestMapping("/page")
    public String queryByPage(ProductQueryPageForm pageForm,Model model) {
        Page<Product> productPage = new Page<>(pageForm.getPage()==0?1:pageForm.getPage(),5,productList);
        model.addAttribute("result",productPage);
        return "page";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
       // Product product1 = new Product(1, "xiaomi", "小米手机", new BigDecimal(1999));
        Product product1 = new Product(1, "xiaomi", "小米手机", new BigDecimal(1999));
        Product product2 = new Product(2, "huawei", "华为", new BigDecimal(4999));
        Product product3 = new Product(3, "honor", "荣耀", new BigDecimal(2999));
        Product product4 = new Product(4, "vivo", "vivo手机", new BigDecimal(3999));
        Product product5 = new Product(5, "oppo", "OPPO手机", new BigDecimal(3299));
        Product product6 = new Product(6, "realme", "realme 手机", new BigDecimal(999));
        Product product7 = new Product(7, "oneplus", "一加手机", new BigDecimal(1999));
        Product product8 = new Product(8, "apple", "苹果手机", new BigDecimal(5999));
        Product product9 = new Product(9, "samsum", "三星手机", new BigDecimal(4999));
        Product product10 = new Product(10, "nokia", "诺基亚手机", new BigDecimal(2999));
        Product product11 = new Product(11, "redmi", "红米手机", new BigDecimal(899));
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);
        productList.add(product6);
        productList.add(product7);
        productList.add(product8);
        productList.add(product9);
        productList.add(product10);
        productList.add(product11);
    }
}
