package com.ch.train.controller;


import com.ch.train.component.datasource.ShardingDatabaseStrategy;
import com.ch.train.entity.Product;
import com.ch.train.form.DataAuthForm;
import com.ch.train.form.IdForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenhuan
 * @since 2023-02-15 10:41:40
 * 根据商户id 查询分库分表策略
 */
@RestController
@RequestMapping("sharding")
public class UserInfoShardingController {

    @Resource
    private ShardingDatabaseStrategy shardingDatabaseStrategy;

    @GetMapping
    public ResponseEntity<String> queryById(DataAuthForm dataAuthForm) {
        return ResponseEntity.ok(this.shardingDatabaseStrategy.getShardDatabaseAndTable(dataAuthForm));
    }
}
