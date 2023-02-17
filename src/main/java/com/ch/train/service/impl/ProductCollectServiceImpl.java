package com.ch.train.service.impl;

import com.ch.train.entity.CollectProduct;
import com.ch.train.entity.Product;
import com.ch.train.service.ProductCollectService;
import com.ch.train.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DXM-0189
 */
@Service
public class ProductCollectServiceImpl implements ProductCollectService {


    @Override
    public CollectProduct collectProduct(String url) throws IOException {
        String html = HttpUtils.httpClientGet(url, null, null);
        Document document = Jsoup.parse(html);
        if(Objects.isNull(document)){
            return null;
        }
        CollectProduct collectProduct = parseProductInfo(document);
        return collectProduct;
    }

    /**
     * 从html 中解析商品信息
     *
     * @param document
     * @return
     */
    private CollectProduct parseProductInfo(Document document) {
        CollectProduct collectProduct = new CollectProduct()
                .setTitle(parseTitle(document))
                .setDesc(parseDesc(document))
                .setPrice(parsePrice(document))
                .setVariations(parseVariation(document))
                .setImages(parseImages(document));
        return collectProduct;
    }


    /**
     * 获取商品名称
     *
     * @return
     */
    private String parseTitle(Document document) {
        Element titleSection = document.getElementById("titleSection");
        String productTitle = titleSection.getElementById("productTitle").text();
        return productTitle;
    }

    /**
     * 获取价格
     *
     * @param document
     * @return
     */
    private String parsePrice(Document document) {
        Element priceDivElement = document.getElementById("corePriceDisplay_desktop_feature_div");
        if(Objects.isNull(priceDivElement)){
            return null;
        }
        String priceText = priceDivElement.select("span.a-offscreen").first().text();
        return priceText;
    }

    /**
     * 解析商品描述信息
     *
     * @param document
     * @return
     */
    private String parseDesc(Document document) {
        Element productDescriptionFeatureDiv = document.getElementById("productDescription_feature_div");
        String productDesc = productDescriptionFeatureDiv.getElementsByTag("span").first().text();
        return productDesc;
    }


    /**
     * 解析商品图片
     *
     * @param document
     * @return
     */
    private List<String> parseImages(Document document) {
        Element imageBlockFeatureDiv = document.getElementById("imageBlock_feature_div");
        if (Objects.isNull(imageBlockFeatureDiv)) {
            return null;
        }
        Elements imageElements = imageBlockFeatureDiv.select("img[src]");
        return Optional.ofNullable(imageElements).orElse(null).stream().map(e -> e.attr("src")).collect(Collectors.toList());
    }

    /**
     * 解析变种信息
     *
     * @param document
     * @return
     */
    private String parseVariation(Document document) {
        Element variationElement = document.getElementById("variation_processor_description");
        if (Objects.isNull(variationElement)) {
            return null;
        }
        Elements elements = variationElement.getElementsByTag("p");
        return Optional.ofNullable(elements).orElse(null).stream().map(e -> e.text()).collect(Collectors.joining("||"));
    }


}
