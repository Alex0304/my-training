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
        Product product1 = new Product(1, "xiaomi", "<script type=\"text/javascript\">alert(1)</script>", new BigDecimal(1999));
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


    /**
     * 新增数据
     *
     * @param product 实体
     * @return 新增结果
     */
    @PostMapping("/toAdd")
    public ResponseEntity<ProductSaveForm> toAdd(ProductSaveForm product) {
        System.out.println(product.getName());
        return ResponseEntity.ok(new ProductSaveForm());
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/update")
    public String edit(Integer id,Model model) {
        Product product = productList.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(new Product());
        model.addAttribute("product",product);
        return "update";
    }

    /**
     * 编辑数据
     *
     * @param product 实体
     * @return 编辑结果
     */
    @PostMapping("/toUpdate")
    public ResponseEntity<ProductSaveForm> toUpdate(ProductSaveForm product) throws BusinessException {
        System.out.println(product.getName());
        return ResponseEntity.ok(new ProductSaveForm());
    }

    /**
     * 删除数据
     *
     * @param idForm 主键
     * @return 删除是否成功
     */
    @PostMapping("/delById")
    public ResponseEntity<Boolean> deleteById(@RequestBody IdForm idForm) {
        System.out.println(idForm.getId());
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
