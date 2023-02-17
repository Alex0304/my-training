package com.ch.train.service;


import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author DXM-0189
 */
public interface ShipProductService {

    /**
     * 保存shipProduct 信息
     * @return
     */
    int saveShipProduct() throws JsonProcessingException;
}
