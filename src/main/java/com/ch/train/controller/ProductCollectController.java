package com.ch.train.controller;


import com.ch.train.entity.CollectProduct;
import com.ch.train.entity.Product;
import com.ch.train.service.ProductCollectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;


/**
 * @author chenhuan
 * 商品采集控制器
 */
@RestController
@RequestMapping("collect")
public class ProductCollectController {

    @Resource
    private ProductCollectService productCollectService;



    /**
     * 根据链接抓取商品信息，不持久化
     * @return
     */
    @GetMapping
    public ResponseEntity<CollectProduct> collectProduct(@RequestParam(value = "url") String url) throws IOException {
        return ResponseEntity.ok(productCollectService.collectProduct(url));
    }

}
