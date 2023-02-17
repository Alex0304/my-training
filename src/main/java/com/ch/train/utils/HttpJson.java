package com.ch.train.utils;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpJson {
	private static final Logger log = Logger.getLogger(HttpJson.class);

	private static final int TIMEOUT         = 5 * 1000; // 连接超时时间

	private static final int SO_TIMEOUT      = 60 * 1000; // 数据传输超时

	public static String get(String strURL,String charset) {
		log.info(strURL);
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Charset",charset);
			connection.setInstanceFollowRedirects(true);
			connection.setReadTimeout(SO_TIMEOUT);
			connection.setConnectTimeout(TIMEOUT);
			connection.setRequestMethod("GET"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/xml"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/xml"); // 设置发送数据的格式
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), charset); // utf-8编码
			//out.append(params);
			out.flush();
			out.close();
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String result = new String(data); // utf-8编码
				log.info(result);
				return result;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error"; // 自定义错误信息
	}
}

