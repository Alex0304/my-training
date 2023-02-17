package com.ch.train.service;

import com.ch.train.entity.Product;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.IdForm;
import com.ch.train.form.ProductQueryPageForm;
import com.ch.train.form.ProductSaveForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 商品表(Product)表服务接口
 *
 * @author chenhuan
 * @since 2023-02-15 10:41:43
 */
public interface ProductService {

    /**
     * 通过ID查询单条数据
     *
     * @param idForm
     * @return 实例对象
     */
    Product queryById(IdForm idForm);

    /**
     * 分页查询
     * @param form
     * @return 查询结果
     */
    Page<Product> queryByPage(ProductQueryPageForm form);

    /**
     * 新增数据
     *
     * @param product 实例对象
     * @return 实例对象
     */
    ProductSaveForm insert(ProductSaveForm product) throws BusinessException;

    /**
     * 修改数据
     *
     * @param product 实例对象
     * @return 实例对象
     */
    ProductSaveForm update(ProductSaveForm product) throws BusinessException;

    /**
     * 通过主键删除数据
     *
     * @param idForm 主键
     * @return 是否成功
     */
    boolean deleteById(IdForm idForm);

}
