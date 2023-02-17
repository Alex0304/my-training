package com.ch.train.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DXM_0005 on 2016/12/17.
 * JSON 工具类
 */
public class JSONUtil {

	/**
	 * Json ObjectMapper
	 */
	private static final ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

	public static String objectToJson(Object obj){
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	/**
	 *  JSON 转换指定 JAVA 对象
	 * @param jsonStr
	 * @param objClass
	 * @param <T>
	 * @return
	 */
	public static <T> T jsonToObject(String jsonStr,Class<T> objClass){
		try {
			return objectMapper.readValue(jsonStr,objClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *  JSON 转换指定 JAVA 对象   抛出异常
	 * @param jsonStr
	 * @param objClass
	 * @param <T>
	 * @return
	 */
	public static <T> T jsonToObjectException(String jsonStr,Class<T> objClass) throws IOException {
		return objectMapper.readValue(jsonStr,objClass);
	}

	/**
	 * JSON 转换指定 JAVA 对象
	 * @param jsonStr
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> T jsonToObject(String jsonStr,TypeReference<T> type){
		try {
			return objectMapper.readValue(jsonStr,type);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(jsonStr);
			return null;
		}
	}

	/**
	 * JSON 转指定Java对象List集合
	 * @param jsonStr
	 * @param clazz
	 * @param <T>
     * @return
     */
	public static <T> List<T> jsonToArray(String jsonStr, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametrizedType(List.class, ArrayList.class, clazz);
		try {
			return mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                    .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(jsonStr, javaType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	//json转java对象，忽略不认识的属性
	public static <T> T jsonToObjectIgnoreUnKnown(String jsonStr,TypeReference<T> type){
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(jsonStr,type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}