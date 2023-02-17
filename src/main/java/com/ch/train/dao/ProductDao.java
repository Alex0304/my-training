package com.ch.train.dao;

import com.ch.train.entity.Product;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.IdForm;
import com.ch.train.form.ProductQueryPageForm;
import com.ch.train.form.ProductSaveForm;

import java.util.List;

/**
 * 商品表(Product)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-15 11:00:47
 */
public interface ProductDao{

    /**
     * 通过ID查询单条数据
     *
     * @param idForm
     * @return 实例对象
     */
    Product queryById(IdForm idForm);

    /**
     * 查询指定行数据
     *
     * @param form 查询条件
     * @return 对象列表
     */
    List<Product> queryAllByLimit(ProductQueryPageForm form);

    /**
     * 统计总行数
     */
    long count(ProductQueryPageForm form);

    /**
     * 新增数据
     *
     * @param product 实例对象
     * @return 影响行数
     */
    int insert(ProductSaveForm product) throws BusinessException;

    /**
     * 修改数据
     *
     * @param product 实例对象
     * @return 影响行数
     */
    int update(ProductSaveForm product) throws BusinessException;

    /**
     * 通过主键删除数据
     *
     * @param idForm
     * @return 影响行数
     */
    int deleteById(IdForm idForm);

}

