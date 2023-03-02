package com.ch.train.controller;


import com.ch.train.component.datasource.ShardingDatabaseStrategy;
import com.ch.train.entity.Product;
import com.ch.train.form.DataAuthForm;
import com.ch.train.form.IdForm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenhuan
 * @since 2023-02-15 10:41:40
 * 根据商户id 查询分库分表策略
 */
@Controller
@RequestMapping("sharding")
public class UserInfoShardingController {

    @Resource
    private ShardingDatabaseStrategy shardingDatabaseStrategy;

    @GetMapping("/queryRuleByUserId")
    public ResponseEntity<String> queryById(DataAuthForm dataAuthForm) {
        String shardDatabaseAndTable = this.shardingDatabaseStrategy.getShardDatabaseAndTable(dataAuthForm);
        return ResponseEntity.ok(shardDatabaseAndTable);
    }

    @GetMapping("/rule")
    public String rule() {
        return "product/userShardRule";
    }
}
