package com.ch.train.dao.impl;

import com.ch.train.builder.InsertConditionBuilder;
import com.ch.train.builder.ProductQueryConditionBuilder;
import com.ch.train.component.datasource.SharedDataSource;
import com.ch.train.component.factory.sql.SqlFactory;
import com.ch.train.component.factory.sql.impl.ProductSqlGenerator;
import com.ch.train.controller.HelloController;
import com.ch.train.dao.ProductDao;
import com.ch.train.entity.Product;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.IdForm;
import com.ch.train.form.ProductQueryPageForm;
import com.ch.train.form.ProductSaveForm;
import com.ch.train.mapper.MyObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.*;
import java.util.List;
import java.util.Objects;


/**
 * @author chenhuan
 * 使用jdbcTemplate 进行数据库访问，进行crud
 */
@Repository
public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {

    private static Log logger = LogFactory.getLog(HelloController.class);

    @Autowired
    public ProductDaoImpl(@Qualifier(value = "shardingSqlFactory") SqlFactory sqlFactory) {
        super(sqlFactory);
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private MyObjectMapper productMapper;


    /**
     * 1.拦截生成sql，包括修改表名
     * 2.设置条件参数
     * 3.查询结果封装对象
     * @param idForm
     * @return
     */
    @Override
    @SharedDataSource
    public Product queryById(IdForm idForm) {
        String baseSql = getBaseSql(ProductSqlGenerator.QUERY_ONE,idForm);
        Integer[] args = new Integer[]{idForm.getId()};
        Product product = jdbcTemplate.queryForObject(baseSql, args, (rs, rowNum) -> productMapper.convertOne(rs));
        return product;
    }

    @Override
    @SharedDataSource
    public List<Product> queryAllByLimit(ProductQueryPageForm form) {
        String baseSql = getBaseSql(ProductSqlGenerator.QUERY_PAGE,form);
        ProductQueryConditionBuilder productConditionBuilder = ProductQueryConditionBuilder.builder(baseSql).where().userIdEquals(form.getUserId()).andNameEquals(form.getName()).orderByUpdateTimeDesc().limit(form.getPage(), form.getSize());
        return jdbcTemplate.query(productConditionBuilder.getSql(), productConditionBuilder.getArgs(), resultSet ->{
            List<Product> productList = productMapper.convertList(resultSet);
            return productList;
        });
    }

    @Override
    @SharedDataSource
    public long count(ProductQueryPageForm form) {
        String baseSql = getBaseSql(ProductSqlGenerator.QUERY_COUNT, form);
        ProductQueryConditionBuilder productConditionBuilder = ProductQueryConditionBuilder.builder(baseSql).where().userIdEquals(form.getUserId()).andNameEquals(form.getName());
        return jdbcTemplate.queryForObject(productConditionBuilder.getSql(), productConditionBuilder.getArgs(), Long.class);
    }

    @Override
    @SharedDataSource
    public int insert(ProductSaveForm saveForm) throws BusinessException {
       String sql = getBaseSql(ProductSqlGenerator.INSERT_ONE,saveForm);
        try {
            Product product = new Product();
            BeanUtils.copyProperties(saveForm,product);
            InsertConditionBuilder builder = InsertConditionBuilder.builder(sql,product);
            jdbcTemplate.update(builder.getSql(),builder.getArgs());
        } catch (DuplicateKeyException ex) {
            throw new BusinessException("商品名称不能重复");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    @SharedDataSource
    public int update(ProductSaveForm product) throws BusinessException {
        if (Objects.isNull(product) || Objects.isNull(product.getId())) {
            return 0;
        }
        String sql = getBaseSql(ProductSqlGenerator.UPDATE_ONE,product);
        try {
            return jdbcTemplate.update(sql, preparedStatement -> {
                Timestamp now = new Timestamp(System.currentTimeMillis());
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getDesc());
                preparedStatement.setBigDecimal(3, product.getPrice());
                preparedStatement.setTimestamp(4, now);
                preparedStatement.setInt(5, product.getId());
            });
        } catch (DuplicateKeyException ex) {
            throw new BusinessException("商品名称不能重复");
        }
    }

    @Override
    @SharedDataSource
    public int deleteById(IdForm idForm) {
        String sql = getBaseSql(ProductSqlGenerator.DELETE_ONE,idForm);
        return jdbcTemplate.update(sql, new Object[]{idForm.getId(),idForm.getUserId()});
    }

}
