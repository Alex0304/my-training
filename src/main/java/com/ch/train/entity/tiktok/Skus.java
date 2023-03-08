package com.ch.train.entity.tiktok;

import java.util.List;

/**
 * @author DXM-0189
 * 变种信息
 */
public class Skus {

    private String sku_id;
    private List<SkuSaleProps> sku_sale_props;
    private String sale_prop_value_ids;
    private int stock;
    private String low_stock_warning;
    private int purchase_limit;
    private SkuPrice price;
    private boolean need_icon;
    private String warehouse_id;

//    private Purchase_notice purchase_notice;
//    private Quantity_property quantity_property;
    private int minimum_buy_quantity;
    private int promotion_limit_quantity;
    private String deduction_text;

    public String getSku_id() {
        return sku_id;
    }

    public Skus setSku_id(String sku_id) {
        this.sku_id = sku_id;
        return this;
    }

    public List<SkuSaleProps> getSku_sale_props() {
        return sku_sale_props;
    }

    public Skus setSku_sale_props(List<SkuSaleProps> sku_sale_props) {
        this.sku_sale_props = sku_sale_props;
        return this;
    }

    public String getSale_prop_value_ids() {
        return sale_prop_value_ids;
    }

    public Skus setSale_prop_value_ids(String sale_prop_value_ids) {
        this.sale_prop_value_ids = sale_prop_value_ids;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Skus setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public String getLow_stock_warning() {
        return low_stock_warning;
    }

    public Skus setLow_stock_warning(String low_stock_warning) {
        this.low_stock_warning = low_stock_warning;
        return this;
    }

    public int getPurchase_limit() {
        return purchase_limit;
    }

    public Skus setPurchase_limit(int purchase_limit) {
        this.purchase_limit = purchase_limit;
        return this;
    }

    public SkuPrice getPrice() {
        return price;
    }

    public Skus setPrice(SkuPrice price) {
        this.price = price;
        return this;
    }

    public boolean isNeed_icon() {
        return need_icon;
    }

    public Skus setNeed_icon(boolean need_icon) {
        this.need_icon = need_icon;
        return this;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public Skus setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
        return this;
    }

    public int getMinimum_buy_quantity() {
        return minimum_buy_quantity;
    }

    public Skus setMinimum_buy_quantity(int minimum_buy_quantity) {
        this.minimum_buy_quantity = minimum_buy_quantity;
        return this;
    }

    public int getPromotion_limit_quantity() {
        return promotion_limit_quantity;
    }

    public Skus setPromotion_limit_quantity(int promotion_limit_quantity) {
        this.promotion_limit_quantity = promotion_limit_quantity;
        return this;
    }

    public String getDeduction_text() {
        return deduction_text;
    }

    public Skus setDeduction_text(String deduction_text) {
        this.deduction_text = deduction_text;
        return this;
    }
}
