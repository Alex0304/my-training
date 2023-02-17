package com.ch.train.service.impl;

import com.ch.train.dao.ShipProductDao;
import com.ch.train.entity.ShipProduct;
import com.ch.train.service.ShipProductService;
import com.ch.train.utils.HttpJson;
import com.ch.train.utils.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author DXM-0189
 */
@Service
public class ShipProductServiceImpl implements ShipProductService {

    @Resource
    private ShipProductDao shipProductDao;

    private static final String SHIP_PRODUCT_URL = "http://123.57.17.48:8082/getProductList.htm";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();



    @Override
    public int saveShipProduct() throws JsonProcessingException {
        String result = HttpJson.get(SHIP_PRODUCT_URL, "UTF-8");
        List<ShipProduct> shipProducts = JSONUtil.jsonToArray(result, ShipProduct.class);
        return shipProductDao.saveShipProductInfo(OBJECT_MAPPER.writeValueAsString(shipProducts));
    }
}
