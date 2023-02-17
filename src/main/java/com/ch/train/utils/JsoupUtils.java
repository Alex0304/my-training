package com.ch.train.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DXM-0189
 */
public class JsoupUtils {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\DXM-0189\\Desktop\\amazon.html");
        Document document = Jsoup.parse(file, "UTF-8");
        Element titleSection = document.getElementById("titleSection");
        String productTitle = titleSection.getElementById("productTitle").text();
        System.out.println(productTitle);
        // 解析价格
        Element priceDivElement = document.getElementById("corePriceDisplay_desktop_feature_div");
        String priceText = priceDivElement.select("span.a-offscreen").first().text();
        System.out.println(priceText);
        // 解析变种信息
        Element variationElement = document.getElementById("variation_processor_description");
        Elements select = variationElement.getElementsByTag("p");
        StringBuilder variationInfos = new StringBuilder();
        for (Element element : select) {
            System.out.println(element.text());
            variationInfos.append(element.text()).append("||");
        }
        System.out.println(variationInfos);
        // 获取商品描述
        Element productDescriptionFeatureDiv = document.getElementById("productDescription_feature_div");
        String productDesc = productDescriptionFeatureDiv.getElementsByTag("span").first().text();
        System.out.println(productDesc);
        System.out.println(document.title());
        // 获取商品图片信息
        Element imageBlockFeatureDiv = document.getElementById("imageBlock_feature_div");
        Elements imageElements = imageBlockFeatureDiv.select("img[src]");
        List<String> pictures = new ArrayList<>();
        for (Element imageElement : imageElements) {
            System.out.println(imageElement.attr("src"));
            pictures.add(imageElement.attr("src"));
        }
        System.out.println(pictures.size());
    }
    
    
}
