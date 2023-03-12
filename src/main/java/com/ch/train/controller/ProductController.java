package com.ch.train.controller;

import com.ch.train.entity.Product;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.IdForm;
import com.ch.train.form.ProductQueryPageForm;
import com.ch.train.form.ProductSaveForm;
import com.ch.train.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 商品表(Product)表控制层
 *
 * @author chenhuan
 * @since 2023-02-15 10:41:40
 */
@RequestMapping("product")
@Controller
public class ProductController {
    /**
     * 服务对象
     */
    @Autowired
    private ProductService productService;


    /**
     * 分页查询
     *
     * @param form 分页对象
     * @return 查询结果
     */
    @RequestMapping("/queryByPage")
    public String queryByPage(ProductQueryPageForm form, Model model) {
        if (Objects.isNull(form.getUserId())) {
            return "product/page";
        }
        Page<Product> productPage = this.productService.queryByPage(form);
        model.addAttribute("result", productPage);
        model.addAttribute("userId", form.getUserId());
        return "product/page";
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

    @GetMapping("/add")
    public String add() {
        return "product/add";
    }

    /**
     * 新增数据
     *
     * @param product 实体
     * @return 新增结果
     */
    @PostMapping("/toAdd")
    public String toAdd(ProductSaveForm product,Model model) throws BusinessException {
        this.productService.insert(product);
        model.addAttribute("userId",product.getUserId());
        return "redirect:/product/queryByPage";
    }


    @GetMapping("/update")
    public String edit(IdForm idForm, Model model) {
        Product product = productService.queryById(idForm);
        model.addAttribute("product", product);
        return "product/update";
    }



    /**
     * 编辑数据
     *
     * @param product 实体
     * @return 编辑结果
     */
    @PostMapping("toUpdate")
    public String edit(ProductSaveForm product,Model model) throws BusinessException {
         this.productService.update(product);
         model.addAttribute("userId",product.getUserId());
         return "redirect:/product/queryByPage";
    }

    /**
     * 删除数据
     *
     * @param idForm 主键
     * @return 删除是否成功
     */
    @PostMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestBody IdForm idForm) {
        return ResponseEntity.ok(this.productService.deleteById(idForm));
    }

    @RequestMapping(value = "/process")
    public String process() {
        return "product/process";
    }

}

