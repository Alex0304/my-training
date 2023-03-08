package com.ch.train.entity.tiktok;

import java.io.Serializable;
import java.util.List;

/**
 * @author DXM-0189
 */
public class TikTokProductInfo implements Serializable {


    private String product_id;
    private int status;
    // 卖家信息
   // private Seller seller;
    // 产品基础信息
    private ProductBase product_base;
    // 产品销售属性
    private List<SaleProps> sale_props;
    // 产品变种集合
    private List<Skus> skus;
    // 物流信息
    //private Logistic logistic;
    // 页面浏览数
    //private Product_detail_review product_detail_review;
    private String customer_service_schema;
    private String chain_key;
    // 加入购物车操作
    //private Add_to_cart_button add_to_cart_button;
    // 卖家id
    private String seller_id;
    // 平台
    private int platform;
    // 来源
    private String source;
    private int source_from;
    // 额外字段
    //private Extra extra;
    // 不可达信息，根据客户手机地理位置，物流不可达，不支持购买
    //private Unavailable_info unavailable_info;
    private String default_address_id;
    private int template;
   // private Marketing_config marketing_config;
   // 运输信息
   //  private Shipping shipping;
    private int sku_style;
    // 支持语言 隐私政策
    //private Terms_of_service terms_of_service;
    //private Privacy_policy privacy_policy;


    public String getProduct_id() {
        return product_id;
    }

    public TikTokProductInfo setProduct_id(String product_id) {
        this.product_id = product_id;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public TikTokProductInfo setStatus(int status) {
        this.status = status;
        return this;
    }

    public ProductBase getProduct_base() {
        return product_base;
    }

    public TikTokProductInfo setProduct_base(ProductBase product_base) {
        this.product_base = product_base;
        return this;
    }

    public List<SaleProps> getSale_props() {
        return sale_props;
    }

    public TikTokProductInfo setSale_props(List<SaleProps> sale_props) {
        this.sale_props = sale_props;
        return this;
    }

    public List<Skus> getSkus() {
        return skus;
    }

    public TikTokProductInfo setSkus(List<Skus> skus) {
        this.skus = skus;
        return this;
    }

    public String getCustomer_service_schema() {
        return customer_service_schema;
    }

    public TikTokProductInfo setCustomer_service_schema(String customer_service_schema) {
        this.customer_service_schema = customer_service_schema;
        return this;
    }

    public String getChain_key() {
        return chain_key;
    }

    public TikTokProductInfo setChain_key(String chain_key) {
        this.chain_key = chain_key;
        return this;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public TikTokProductInfo setSeller_id(String seller_id) {
        this.seller_id = seller_id;
        return this;
    }

    public int getPlatform() {
        return platform;
    }

    public TikTokProductInfo setPlatform(int platform) {
        this.platform = platform;
        return this;
    }

    public String getSource() {
        return source;
    }

    public TikTokProductInfo setSource(String source) {
        this.source = source;
        return this;
    }

    public int getSource_from() {
        return source_from;
    }

    public TikTokProductInfo setSource_from(int source_from) {
        this.source_from = source_from;
        return this;
    }

    public String getDefault_address_id() {
        return default_address_id;
    }

    public TikTokProductInfo setDefault_address_id(String default_address_id) {
        this.default_address_id = default_address_id;
        return this;
    }

    public int getTemplate() {
        return template;
    }

    public TikTokProductInfo setTemplate(int template) {
        this.template = template;
        return this;
    }

    public int getSku_style() {
        return sku_style;
    }

    public TikTokProductInfo setSku_style(int sku_style) {
        this.sku_style = sku_style;
        return this;
    }
}
