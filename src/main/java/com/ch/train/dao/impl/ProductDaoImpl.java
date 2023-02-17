package com.ch.train.dao.impl;

import com.ch.train.component.datasource.SharedDataSource;
import com.ch.train.controller.HelloController;
import com.ch.train.dao.ProductDao;
import com.ch.train.entity.Product;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.DataAuthForm;
import com.ch.train.form.IdForm;
import com.ch.train.form.ProductQueryPageForm;
import com.ch.train.form.ProductSaveForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class ProductDaoImpl extends AbstractShardTableDao<Product> implements ProductDao {

    private static Log logger = LogFactory.getLog(HelloController.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ONE = "select * from product\n" +
            "where id=?";

    private static final String INSERT_SQL = "insert into product(name,`desc`,price,user_id,create_time,update_time) values (?,?,?,?,?,?);";
    private static final String UPDATE_SQL = "update product set name = ?,`desc` =?,price =?,update_time = ? where id =?;";
    private static final String DELETE_SQL = "delete from product  where id = ?;";
    private static final String PAGE_SQL = "select id,name,`desc`,price,create_time,update_time,user_id from product";
    private static final String COUNT_SQL = "select count(1) from product";


    @Override
    @SharedDataSource
    public Product queryById(IdForm idForm) {
        String sql = replaceSql(SELECT_ONE,Product.class,idForm);
        Integer[] args = new Integer[]{idForm.getId()};
        Product product = null;
        try {
            product = jdbcTemplate.queryForObject(sql, args, (rs, rowNum) -> {
                Product temp = new Product();
                temp.setId(rs.getInt("id"));
                temp.setName(rs.getString("name"));
                temp.setPrice(rs.getBigDecimal("price"));
                temp.setDesc(rs.getString("desc"));
                temp.setCreateTime(rs.getTimestamp("create_time"));
                temp.setUpdateTime(rs.getTimestamp("update_time"));
                temp.setUserId(rs.getInt("user_id"));
                return temp;
            });
        } catch (EmptyResultDataAccessException e) {
            logger.error("查询的商品信息不存在");
        }

        return product;
    }

    @Override
    @SharedDataSource
    public List<Product> queryAllByLimit(ProductQueryPageForm form) {
        String replaceSql = replaceSql(PAGE_SQL,Product.class,form);
        StringBuilder sql = new StringBuilder(replaceSql);
        String name = form.getName();
        ArrayList<Object> params = new ArrayList<>();
        if (StringUtils.isNotBlank(name)) {
            params.add(form.getName());
            sql.append(" where name = ? ");
        }
        sql.append(" limit ?,? ");
        params.add(form.getPage());
        params.add(form.getSize());
        List<Product> productList = jdbcTemplate.query(sql.toString(), params.toArray(), resultSet -> {
            List<Product> list = new ArrayList<>();
            while (resultSet.next()) {
                Product temp = new Product();
                temp.setId(resultSet.getInt("id"));
                temp.setName(resultSet.getString("name"));
                temp.setPrice(resultSet.getBigDecimal("price"));
                temp.setDesc(resultSet.getString("desc"));
                temp.setCreateTime(resultSet.getTimestamp("create_time"));
                temp.setUpdateTime(resultSet.getTimestamp("update_time"));
                temp.setUserId(resultSet.getInt("user_id"));
                list.add(temp);
            }
            return list;
        });
        return productList;
    }

    @Override
    @SharedDataSource
    public long count(ProductQueryPageForm form) {
        String replaceSql = replaceSql(COUNT_SQL,Product.class,form);
        StringBuilder sql = new StringBuilder(replaceSql);
        String name = form.getName();
        ArrayList<Object> params = new ArrayList<>();
        if (StringUtils.isNotBlank(name)) {
            params.add(form.getName());
            sql.append(" where name =?");
        }
        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Long.class);
    }

    @Override
    @SharedDataSource
    public int insert(ProductSaveForm product) throws BusinessException {
        String sql = replaceSql(INSERT_SQL,Product.class,product);
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
        String sql = replaceSql(UPDATE_SQL,Product.class,product);
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
        String sql = replaceSql(DELETE_SQL,Product.class,idForm);
        return jdbcTemplate.update(sql, new Object[]{idForm.getId()});
    }

}
