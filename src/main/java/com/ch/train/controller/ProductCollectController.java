package com.ch.train.controller;


import com.ch.train.entity.CollectProduct;
import com.ch.train.entity.Product;
import com.ch.train.service.ProductCollectService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;


/**
 * @author chenhuan
 * 商品采集控制器
 */
@Controller
@RequestMapping("product")
public class ProductCollectController {

    @Resource
    private ProductCollectService productCollectService;



    /**
     * 根据链接抓取商品信息，不持久化
     * @return
     */
    @GetMapping("/toCollect")
    public String toCollectProduct(@RequestParam(value = "url") String url, Model model) throws IOException {
        CollectProduct collectProduct = productCollectService.collectProduct(url);
        model.addAttribute("collectProduct",collectProduct);
        return "product/collectProduct";
    }

    @GetMapping("/collect")
    public String collect() throws IOException {
        return "product/collect";
    }

    public static void main(String[] args) {
        long f3 = Long.parseLong("f3", 16);
        System.out.println(f3);
    }

}
