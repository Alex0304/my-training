package com.ch.train.controller;


import com.ch.train.entity.Product;
import com.ch.train.entity.ShipProduct;
import com.ch.train.service.ShipProductService;
import com.ch.train.utils.HttpJson;
import com.ch.train.utils.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品表(Product)表控制层
 *
 * @author chenhuan
 * @since 2023-02-15 10:41:40
 */
@RestController
@RequestMapping("shipProduct")
public class ShipProductController {


    @Resource
    private ShipProductService shipProductService;


    /**
     * 根据链接抓取商品信息，不持久化
     * @return
     */
    @GetMapping
    public ResponseEntity<Integer> shipProduct() throws JsonProcessingException {
        return ResponseEntity.ok(shipProductService.saveShipProduct());
    }

}
