package com.ch.train.component.filter;

import cn.hutool.core.util.EscapeUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Arrays;


/**
 * @author DXM-0189
 * 对request 请求对象进行包装，处理请求参数，对html 标签进行过滤
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {


    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }


    /**
     * 对请求头参数进行xss 参数过滤
     * @param name
     * @return
     */
    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        return EscapeUtil.escape(header);
    }


    /**
     * 根据参数名获取参数值
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        if(StringUtils.isNotBlank(parameter)){
            return EscapeUtil.escapeHtml4(parameter);
        }
      return parameter;
    }


    /**
     * 根据参数名获取数组
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null || parameterValues.length == 0) {
            return parameterValues;
        }
        return Arrays.stream(parameterValues).map(e -> EscapeUtil.escapeHtml4(e)).toArray(String[]::new);
    }
}
