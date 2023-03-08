package com.ch.train.utils;

import cn.hutool.core.bean.BeanUtil;
import com.ch.train.entity.tiktok.TikTokProductInfo;
import com.ch.train.entity.tiktok.TiktokData;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import org.springframework.cglib.beans.BeanMap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author DXM-0189
 */
public class JPathUtils {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\DXM-0189\\Desktop\\tiktokjson.json");
        StringBuilder jsonFile = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(file)){
            int len=0;
            byte[] buffer=new byte[1024];
            while ((len=fileInputStream.read(buffer))!=-1){
                jsonFile.append(new String(buffer,0,len));
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TiktokData tiktokData = objectMapper.readValue(jsonFile.toString(), TiktokData.class);
        }catch (Exception e){

        }
        
        JSONUtil.jsonToObject(jsonFile.toString(), TiktokData.class);
        Map<String,String> resultMap = JSONUtil.jsonToObject(jsonFile.toString(), Map.class);
       // List<String> authors = JsonPath.read(jsonFile.toString(), "$.store.book[*].author");
        //List<Map<String,?>> mapList = JsonPath.parse(jsonFile.toString()).read( "$..initialData.productInfo");

        //TikTokProductInfo tikTokProductInfo = BeanUtil.mapToBean(mapList.get(0), TikTokProductInfo.class, true);
       // System.out.println(tikTokProductInfo.getProduct_id());
    }


}
