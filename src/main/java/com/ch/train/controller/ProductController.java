package com.ch.train.controller;

import com.ch.train.entity.Product;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.IdForm;
import com.ch.train.form.ProductQueryPageForm;
import com.ch.train.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商品表(Product)表控制层
 *
 * @author chenhuan
 * @since 2023-02-15 10:41:40
 */
@RestController
@RequestMapping("product")
public class ProductController {
    /**
     * 服务对象
     */
    @Resource
    private ProductService productService;

    /**
     * 分页查询
     *
     * @param form      分页对象
     * @return 查询结果
     */
    @PostMapping("/queryByPage")
    public ResponseEntity<Page<Product>> queryByPage(@RequestBody ProductQueryPageForm form) {
        return ResponseEntity.ok(this.productService.queryByPage(form));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param idForm 主键
     * @return 单条数据
     */
    @GetMapping
    public ResponseEntity<Product> queryById(IdForm idForm) {
        return ResponseEntity.ok(this.productService.queryById(idForm));
    }

    /**
     * 新增数据
     *
     * @param product 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Product> add(Product product) throws BusinessException {
        return ResponseEntity.ok(this.productService.insert(product));
    }

    /**
     * 编辑数据
     *
     * @param product 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Product> edit(Product product) throws BusinessException {
        return ResponseEntity.ok(this.productService.update(product));
    }

    /**
     * 删除数据
     *
     * @param idForm 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(IdForm idForm) {
        return ResponseEntity.ok(this.productService.deleteById(idForm));
    }

}

