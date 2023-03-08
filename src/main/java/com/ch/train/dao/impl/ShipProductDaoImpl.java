package com.ch.train.dao.impl;

import com.ch.train.dao.ShipProductDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

/**
 * @author DXM-0189
 */
@Repository
public class ShipProductDaoImpl implements ShipProductDao {


    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String SAVE_URL ="insert into ship_product_info(ship_product_str, create_time) values (?,?);";


    @Override
    public int saveShipProductInfo(String shipProductInfo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_URL, new String[]{"id"});
            Timestamp now = new Timestamp(System.currentTimeMillis());
            preparedStatement.setString(1, shipProductInfo);
            preparedStatement.setTimestamp(2, now);
            return preparedStatement;
        },keyHolder);
        return keyHolder.getKey().intValue();
    }

}
