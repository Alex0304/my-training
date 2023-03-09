package com.ch.train.dao.impl;

import com.ch.train.builder.ProductConditionBuilder;
import com.ch.train.component.datasource.SharedDataSource;
import com.ch.train.component.factory.sql.SqlFactory;
import com.ch.train.component.factory.sql.impl.ProductSqlGenerator;
import com.ch.train.component.factory.sql.impl.ShardingSqlFactory;
import com.ch.train.controller.HelloController;
import com.ch.train.dao.ProductDao;
import com.ch.train.entity.Product;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.DataAuthForm;
import com.ch.train.form.IdForm;
import com.ch.train.form.ProductQueryPageForm;
import com.ch.train.form.ProductSaveForm;
import com.ch.train.mapper.MyObjectMapper;
import com.ch.train.mapper.ProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
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
//        try {
//            product = jdbcTemplate.queryForObject(baseSql, args, (rs, rowNum) -> {
//                Product temp = new Product();
//                temp.setId(rs.getInt("id"));
//                temp.setName(rs.getString("name"));
//                temp.setPrice(rs.getBigDecimal("price"));
//                temp.setDesc(rs.getString("desc"));
//                temp.setCreateTime(rs.getTimestamp("create_time"));
//                temp.setUpdateTime(rs.getTimestamp("update_time"));
//                temp.setUserId(rs.getInt("user_id"));
//                return temp;
//            });
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("查询的商品信息不存在");
//        }
        return product;
    }

    @Override
    @SharedDataSource
    public List<Product> queryAllByLimit(ProductQueryPageForm form) {
        String baseSql = getBaseSql(ProductSqlGenerator.QUERY_PAGE,form);
//        StringBuilder sql = new StringBuilder(baseSql);
//        ArrayList<Object> params = new ArrayList<>();
//        if(Objects.nonNull(form.getUserId())){
//            params.add(form.getUserId());
//            sql.append(" where user_id = ? ");
//        }
//        sql.append("order by update_time desc");
//        sql.append(" limit ?,? ");
//        int page = form.getPage();
//        int size = form.getSize();
//        if(page<=1){
//            params.add(0);
//        }else {
//            params.add((page -1)*size);
//        }
//        params.add(size);
        ProductConditionBuilder productConditionBuilder = ProductConditionBuilder.builder(baseSql).where().userIdEquals(form.getUserId()).andNameEquals(form.getName()).orderByUpdateTimeDesc().limit(form.getPage(), form.getSize());
        return jdbcTemplate.query(productConditionBuilder.getCondition(), productConditionBuilder.getArgs(), resultSet ->{
            List<Product> productList = productMapper.convertList(resultSet);
            return productList;
        });
//        List<Product> productList = jdbcTemplate.query(sql.toString(), params.toArray(), resultSet -> {
//            List<Product> list = new ArrayList<>();
//            while (resultSet.next()) {
//                Product temp = new Product();
//                temp.setId(resultSet.getInt("id"));
//                temp.setName(resultSet.getString("name"));
//                temp.setPrice(resultSet.getBigDecimal("price"));
//                temp.setDesc(resultSet.getString("desc"));
//                temp.setCreateTime(resultSet.getTimestamp("create_time"));
//                temp.setUpdateTime(resultSet.getTimestamp("update_time"));
//                temp.setUserId(resultSet.getInt("user_id"));
//                list.add(temp);
//            }
//            return list;
//        });
     //   return productList;
    }

    @Override
    @SharedDataSource
    public long count(ProductQueryPageForm form) {
        String baseSql = getBaseSql(ProductSqlGenerator.QUERY_COUNT, form);
//        StringBuilder sql = new StringBuilder(baseSql);
//        ArrayList<Object> params = new ArrayList<>();
//        if(Objects.nonNull(form.getUserId())){
//            params.add(form.getUserId());
//            sql.append(" where user_id = ? ");
//        }
        ProductConditionBuilder productConditionBuilder = ProductConditionBuilder.builder(baseSql).where().userIdEquals(form.getUserId()).andNameEquals(form.getName());
        return jdbcTemplate.queryForObject(productConditionBuilder.getCondition(), productConditionBuilder.getArgs(), Long.class);
    }

    @Override
    @SharedDataSource
    public int insert(ProductSaveForm product) throws BusinessException {
        String sql = getBaseSql(ProductSqlGenerator.INSERT_ONE,product);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
                Timestamp now = new Timestamp(System.currentTimeMillis());
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getDesc());
                preparedStatement.setBigDecimal(3, product.getPrice());
                preparedStatement.setInt(4, product.getUserId());
                preparedStatement.setTimestamp(5, now);
                preparedStatement.setTimestamp(6, now);
                return preparedStatement;
            }, keyHolder);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException("商品名称不能重复");
        }
        return keyHolder.getKey().intValue();
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
