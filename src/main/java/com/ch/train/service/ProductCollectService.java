package com.ch.train.service;

import com.ch.train.entity.CollectProduct;
import com.ch.train.entity.Product;

import java.io.IOException;

/**
 * @author DXM-0189
 */
public interface ProductCollectService {

    CollectProduct collectProduct(String url) throws IOException;
}
