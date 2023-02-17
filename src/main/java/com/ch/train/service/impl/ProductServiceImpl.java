package com.ch.train.service.impl;

import com.ch.train.entity.Product;
import com.ch.train.dao.ProductDao;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.IdForm;
import com.ch.train.form.ProductQueryPageForm;
import com.ch.train.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 商品表(Product)表服务实现类
 *
 * @author chenhuan
 * @since 2023-02-15 10:41:44
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductDao productDao;


    /**
     * 通过ID查询单条数据
     *
     * @param idForm
     * @return 实例对象
     */
    @Override
    public Product queryById(IdForm idForm) {
        return this.productDao.queryById(idForm);
    }

    /**
     * 分页查询
     *
     * @param form
     * @return 查询结果
     */
    @Override
    public Page<Product> queryByPage(ProductQueryPageForm form) {
        long total = this.productDao.count(form);
        if (total == 0) {
            return null;
        }
        return new PageImpl<>(this.productDao.queryAllByLimit(form), new PageRequest(form.getPage(), form.getSize()), total);
    }

    /**
     * 新增数据
     *
     * @param product 实例对象
     * @return 实例对象
     */
    @Override
    public Product insert(Product product) throws BusinessException {
        this.productDao.insert(product);
        return product;
    }

    /**
     * 修改数据
     *
     * @param product 实例对象
     * @return 实例对象
     */
    @Override
    public Product update(Product product) throws BusinessException {
        productDao.update(product);
        return product;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(IdForm idForm) {
        return this.productDao.deleteById(idForm) > 0;
    }
}
