package com.ch.train.utils;

import com.ch.train.entity.tiktok.TiktokData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author DXM-0189
 */
public class TiktokCrawlUtils {
    public static void main(String[] args) throws JsonProcessingException, UnsupportedEncodingException, UnsupportedEncodingException {
//       String s = HttpUtils.httpClientGet("https://vt.tiktok.com/ZS8uh59TX/",null,null);
//        //String s = HttpUtils.httpClientGet("https://www.baidu.com",null,null);
//        System.out.println(s);

        String result = "";
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse res = null;
        String redirectUrl ="";
        TiktokData tikTokProductInfo;
        try {

            HttpClientContext context = HttpClientContext.create();
            // 创建一个httpClient对象
            httpclient = HttpClients.createDefault();
            //登陆 从配置文件中读取url(也可以写成参数)
            HttpGet httpGet = new HttpGet("https://vt.tiktok.com/ZS8uh59TX");
            // 设置代理HttpHost
            HttpHost proxy = new HttpHost("127.0.0.1", 7890, "http");
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            // 设置参数
            httpGet.setConfig(config);
            // 得到响应状态码
            res = httpclient.execute(httpGet,context);
            //  获取返回对象
            HttpEntity entity = res.getEntity();
            // 通过EntityUtils获取返回结果内容
            result = EntityUtils.toString(entity);
            Document parse = Jsoup.parse(result);
            String render_data = parse.getElementById("RENDER_DATA").data();
            tikTokProductInfo = JSONUtil.jsonToObject(URLDecoder.decode(render_data,"utf-8"),TiktokData.class);
            List<URI> redirectLocations = context.getRedirectLocations();
            URI uri = redirectLocations.get(0);
            redirectUrl = URLDecoder.decode(uri.toString(), "utf-8");
            System.out.println(tikTokProductInfo.getProductDetail().getInitialData().getProductInfo().getProduct_id());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //  最后一定要释放资源
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //https://www.tiktok.com/view/product/1729589057877215991?trackParams={"shop_container_id":"1677068621121-54bc3080-fbb8-4c6d-8286-dc630e483322","original_price":"Rp589.900","enter_from":"others_homepage","search_result_id":"","click_product_start_time":690840931,"follow_status":"1","enter_from_info":"product_share_outside","previous_page":"shop","item_order":3,"click_scene_start_time":690840931,"traffic_source_list":[7],"product_id":"1729589057877215991","shop_status":"2","product_source":5,"platform_extension":"{\"level1\":\"0\"}","author_type":"official","traffic_source":7,"sale_price":"Rp259.900","search_id":"","shop_id":"7494910468218456823","product_type":"1","enter_method":"click_button","entrance_form":"","purchase_path":"normal","track_id":"shop_Profile_1034338801255_1729589057877215991_3","is_single_sku":0,"source_previous_page":"others_homepage","template_id":"","source_page_type":"","source_content_id":"","entrance_type":"top_button","EVENT_ORIGIN_FEATURE":"TEMAI","page_name":"product_detail","page_show_type":"full_screen","delivery_option":1}&unique_id=kevinbsbs&u_code=dj5g9d6agbj66k&timestamp=1677068954&user_id=6974294692783817733&sec_user_id=MS4wLjABAAAAwJr3KcW9HDLyN57pMrn_-u2BiSeOngw2x2B4OO6bAHJ3w1DpyvZB2VRIFuVeahwe&utm_source=copy&utm_campaign=client_share&utm_medium=android&share_iid=7202953223765542682&share_link_id=bf54df58-5c69-4be0-85e5-1a29b7e6a9d6&share_app_id=1233&ugbiz_name=Unknown&ug_btm=b5836,b6661
        //从重定向的uri 中分析出产品的参数和站点
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(redirectUrl).build();
        MultiValueMap<String, String> queryParams = uriComponents.getQueryParams();
        String trackParams = "";
        for (Map.Entry<String, List<String>> stringListEntry : queryParams.entrySet()) {
            String key = stringListEntry.getKey();
            if(key.equals("trackParams")){
                trackParams = stringListEntry.getValue().get(0);
                break;
            }
            List<String> value = stringListEntry.getValue();
            System.out.println(key+":"+value.stream().collect(Collectors.joining(",")));
        }
        Map<String,Object> map = JSONUtil.jsonToObject(trackParams, Map.class);
        String price = map.get("original_price").toString();
        String productId = map.get("product_id").toString();

//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            System.out.println(key+":"+value.toString());
//            if(key.equals("original_price")){
//                price = value.toString();
//                break;
//            }
//        }
        String country = "";
        if(price.startsWith("Rp")){
            country = "ID";
        }
        String template ="https://shop.tiktok.com/view/product/{0}?region={1}";
        String url = MessageFormat.format(template,productId,country);
        System.out.println(url);
        String productResult = "";
        CloseableHttpClient productHttpclient = null;
        CloseableHttpResponse productRes = null;
        try {

            HttpClientContext context = HttpClientContext.create();
            // 创建一个httpClient对象
            productHttpclient = HttpClients.createDefault();
            //登陆 从配置文件中读取url(也可以写成参数)
            HttpGet httpGet = new HttpGet(url);
            // 设置代理HttpHost
            HttpHost proxy = new HttpHost("127.0.0.1", 7890, "http");
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            // 设置参数
            httpGet.setConfig(config);
            // 得到响应状态码
            productRes = productHttpclient.execute(httpGet,context);
            //  获取返回对象
            HttpEntity entity = productRes.getEntity();
            // 通过EntityUtils获取返回结果内容
            productResult = EntityUtils.toString(entity);
            // System.out.println(productResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //  最后一定要释放资源
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Document parse = Jsoup.parse(productResult);
        String renderData = parse.getElementById("RENDER_DATA").data();
        //    System.out.println(renderData);
        String decodeProductInfo = URLDecoder.decode(renderData,"utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TiktokData tiktokData = objectMapper.readValue(decodeProductInfo, TiktokData.class);
        System.out.println(tiktokData.getProductDetail().getInitialData().getProductInfo().getProduct_id());
    }
}
