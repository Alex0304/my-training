package com.ch.train.utils;


import org.apache.http.*;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.CookieStore;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static RequestConfig config;

	private static PoolingHttpClientConnectionManager pool;

	private static CloseableHttpClient splider;

	private static CookieStore cookieStore ;


	private static final int REQUEST_TIMEOUT = 5 * 1000; // 设置请求超时

	public static final int TIMEOUT         = 5 * 1000; // 连接超时时间

	public static final int SO_TIMEOUT      = 15 * 1000; // 数据传输超时



	static {
		RequestConfig defaultConfig = RequestConfig.custom()
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
		config = RequestConfig.copy(defaultConfig)
				.setSocketTimeout(SO_TIMEOUT)
				.setConnectTimeout(TIMEOUT)
				.setConnectionRequestTimeout(REQUEST_TIMEOUT)
				.build();

		pool = new PoolingHttpClientConnectionManager();
		pool.setDefaultMaxPerRoute(20);
		pool.setMaxTotal(2000);
		splider = HttpClients.custom().setConnectionManager(pool).build();
	}

	/**
	 *基于HttpClient GET 请求
	 * @param url 指定URL地址
	 * @param params  参数
	 * @param cookie
	 * @return
	 */
	public static String httpClientGet(String url, Map<String, String> params, Map<String, String> cookie) {
		CloseableHttpResponse response = null;
		try {
			// 构造参数
			StringBuffer sbUrl = getRquestUrl(url,params);

			HttpGet httpGet = new HttpGet(sbUrl.toString());
			httpGet.setConfig(config);

			// 设置参数
			if (cookie != null) {
				StringBuffer csb = new StringBuffer();
				for(String key:cookie.keySet()) {
					csb.append(key + "=" + cookie.get(key) + ";");
				}
				httpGet.setHeader("Cookie", csb.toString());
			}
			response = splider.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			} else {
				logger.info("httpClientGet [" + url + "]没有请求到数据!");
			}
			return "";

		} catch (Exception e) {
			logger.error("httpClientGet [" + url + "] 出现异常！",e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				logger.error("httpClientGet response.close() 出现异常！",e);
			}
		}
		return "";
	}

	private static void setEntity(HttpPost httpPost, Map<String, String> params){
		if(params != null && params.size() > 0) {
			List<NameValuePair> nvps = new ArrayList<>();
			for(String key:params.keySet()) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
			httpPost.setEntity(formEntity);
		}
	}

	/**
	 * 基于HttpClient POST 请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpClientPost(String url,Map<String, String> headers,Map<String, String> params) {
		CloseableHttpResponse response = null;
		try {

			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(config);
			// 构造参数
			setEntity(httpPost,params);
			if(headers!=null && headers.size()>0){
				for(String key:headers.keySet()){
					httpPost.setHeader(key,headers.get(key));
				}
			}
			response = splider.execute(httpPost);



			if(response.getStatusLine().getStatusCode()==302){
				Header[] hs = response.getHeaders("Location");
				if(hs.length>0){
					url = hs[0].toString();
					httpPost = new HttpPost(url);
					httpPost.setConfig(config);
					// 构造参数
					setEntity(httpPost,params);
					if(headers!=null && headers.size()>0){
						for(String key:headers.keySet()){
							httpPost.setHeader(key,headers.get(key));
						}
					}
					response = splider.execute(httpPost);
				}
			}


			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, Consts.UTF_8);
			} else {
				logger.info("httpClientPost 没有获取到数据！");
			}
			return "";

		} catch (Exception e) {
			logger.info("httpClientPost ["+url+"] 请求出现异常",e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
			}
		}
		return "";
	}
	/**
	 * 基于HttpClient POST取cookie 请求
	 * @param url      url
	 * @param params	params
	 * @param proxy	   代理
	 * @return
	 */
	public static List<Cookie> getPostCookie(String url, Map<String, String> headers, Map<String, String> params, HttpHost proxy) {
		CloseableHttpResponse response = null;
		try {

			HttpPost httpPost = new HttpPost(url);
			config = RequestConfig.copy(config).setProxy(proxy).build();

			httpPost.setConfig(config);
			// 构造参数
			setEntity(httpPost,params);
			if(headers!=null && headers.size()>0){
				for(String key:headers.keySet()){
					httpPost.setHeader(key,headers.get(key));
				}
			}
			HttpClientContext context = HttpClientContext.create();
			cookieStore = (CookieStore) new BasicCookieStore();
			context.setCookieStore((org.apache.http.client.CookieStore) cookieStore);
			response = splider.execute(httpPost,context);

			List<Cookie> cookies = context.getCookieStore().getCookies();
			for (Cookie cookie1 : cookies) {
				System.out.println(cookie1.getName()+"："+cookie1.getValue());
			}

			return cookies;

		} catch (Exception e) {
			logger.info("httpClientPost Cookies["+url+"] 请求出现异常",e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 基于HttpClient POST 请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpClientPost(String url, Map<String, String> params) {
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(config);
			// 构造参数
			setEntity(httpPost,params);
			response = splider.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, Consts.UTF_8);
			} else {
				logger.info("httpClientPost 没有获取到数据！");
			}
			return "";

		} catch (Exception e) {
			logger.info("httpClientPost ["+url+"] 请求出现异常",e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
			}
		}
		return "";
	}

	private  static  StringBuffer getRquestUrl(String url, Map<String, String> params) throws Exception{
		// 构造参数
		StringBuffer sbUrl = new StringBuffer(url);
		if (params!= null) {
			sbUrl.append("?");
			for(String key:params.keySet()) {
				sbUrl.append(key + "=" + URLEncoder.encode(params.get(key), "UTF-8") + "&");
			}
			sbUrl.delete(sbUrl.length() - 1, sbUrl.length());
		}
		return sbUrl;
	}

	/**
	 *基于HttpProxyClient GET 请求
	 * @param url 指定URL地址
	 * @param params  参数
	 * @return
	 */
	public static String httpProxyClientGet(String proxyIp,int port,String url, Map<String, String> params) {
		CloseableHttpResponse response = null;
		String sbUrl = null;
		try {
			// 构造参数
			sbUrl = getRquestUrl(url,params).toString();
			HttpClientBuilder build = HttpClients.custom();
			HttpHost proxy = new HttpHost(proxyIp,port);
			CloseableHttpClient client = build.setDefaultRequestConfig(config).setProxy(proxy).build();
			HttpGet request = new HttpGet(sbUrl);
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			} else {
				logger.info("httpClientGet [" + sbUrl + "]没有请求到数据!");
			}
			return "";

		} catch (Exception e) {
			logger.error("httpClientGet [" + sbUrl + "] 出现异常！",e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				logger.error("httpClientGet response.close() 出现异常！",e);
			}
		}
		return "";
	}



//	public static void main(String[] args) throws Exception{
//		String requestUrl = "http://gw.api.alibaba.com/openapi/param2/2/portals.open/api.getPromotionLinks/32832";
//		Map<String,String> parMap = new HashMap<>();
//		parMap.put("fields","promotionUrl");
//		parMap.put("trackingId","shopmaster");
//		parMap.put("urls","https://it.aliexpress.com/store/product/Vfemage-Delle-Donne-Autunno-Inverno-Elegante-gonna-A-Pieghe-Collo-Patchwork-Lavoro-Occasionale-Ufficio-Affari-Cocktail/1248036_32833805587.html?spm=a2g0y.12010612.0.0.674e02b8M3VvFD");
//		System.out.println(httpClientPost(requestUrl,parMap));
//		String url = URLEncoder.encode("https://it.aliexpress.com/store/product/Vfemage-Delle-Donne-Autunno-Inverno-Elegante-gonna-A-Pieghe-Collo-Patchwork-Lavoro-Occasionale-Ufficio-Affari-Cocktail/1248036_32833805587.html?spm=a2g0y.12010612.0.0.674e02b8M3VvFD","utf-8");
//		System.out.println(url);
//		getTopDomain("1234567");
//	}

}
