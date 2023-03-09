package com.ch.train.mapper;

import com.ch.train.entity.Product;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

/**
 * @author DXM-0189
 */
@Component
public class ProductMapper extends AbstractObjectMapper implements MyObjectMapper{


    @Override
    public List<Product> convertList(ResultSet resultSet) {
        List<Product> products = extractData(resultSet, Product.class);
        return products;
    }

    @Override
    public Product convertOne(ResultSet resultSet) {
        List<Product> products = this.convertList(resultSet);
        return Optional.ofNullable(products).orElse(null).get(0);
    }
}
