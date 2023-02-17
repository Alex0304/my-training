package com.ch.train.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ProcessMsg implements Serializable {

    private static final long serialVersionUID = -4732484197154257579L;

    private Integer code;                   // 代码(0: 执行中; 1: 执行完成; -1: 执行结果错误; -2: 数据不存在)
    private String msg = "";                // 执行信息
    private Object data;                    // 存放数据
    private Object extraData;               // 存放数据
    private int successNum;                 // 成功数量
    private String successProductId;        // 成功 productID;
    private int failedNum;                  // 失败数量
    private int repeatedNum;                // 重复数量
    private String rerepeatedURL;           // 重复产品URL
    private int totalNum;                   // 总数量
    private Map<String,Object> dateMap;     // 数据MAP
    private Date createDate = new Date();   // 创建时间


    public ProcessMsg() {
    }

    public ProcessMsg(Integer code) {
        this.code = code;
    }

    public ProcessMsg(Integer code, int successNum, String msg) {
        this.code = code;
        this.successNum = successNum;
        this.msg = msg;
    }

    public ProcessMsg(Integer code, int successNum, int failedNum, String msg) {
        this(code, successNum, msg);
        this.failedNum = failedNum;
        this.msg = msg;
    }

    public ProcessMsg(Integer code, int successNum, int failedNum, int repeatedNum , Date date) {
        this.code = code;
        this.successNum = successNum;
        this.failedNum = failedNum;
        this.repeatedNum = repeatedNum;
        if(date!=null) {
            this.data = date;
        }
    }

    public ProcessMsg(Integer code, int successNum, int failedNum, int totalNum) {
        this.code = code;
        this.successNum = successNum;
        this.failedNum = failedNum;
        this.totalNum = totalNum;
    }

    public Map<String, Object> getDateMap() {
        return dateMap;
    }

    public void setDateMap(Map<String, Object> dateMap) {
        this.dateMap = dateMap;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    public int getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(int successNum) {
        this.successNum = successNum;
    }

    public int getFailedNum() {
        return failedNum;
    }

    public void setFailedNum(int failedNum) {
        this.failedNum = failedNum;
    }

    public int getRepeatedNum() { return repeatedNum; }

    public void setRepeatedNum(int repeatedNum) { this.repeatedNum = repeatedNum; }

    public String getRerepeatedURL() { return rerepeatedURL; }

    public void setRerepeatedURL(String rerepeatedURL) { this.rerepeatedURL = rerepeatedURL; }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getSuccessProductId() {
        return successProductId;
    }

    public void setSuccessProductId(String successProductId) {
        this.successProductId = successProductId;
    }

    public ProcessMsg setProcessMsg(Integer code, int successNum, int failedNum, String msg) {
        this.code = code;
        this.totalNum = successNum + failedNum;
        this.successNum = successNum;
        this.failedNum = failedNum;
        this.msg = msg;
        this.createDate = new Date();
        return this;
    }

    /**
     * 当有重复时，设置包含重复的采集过程
     * @param code 状态码
     * @param successNum 成功数量
     * @param failedNum 失败数量
     * @param repeatedNum 重复数量
     * @param msg 采集消息
     * @return
     */
    public ProcessMsg setProcessMsg(Integer code, int successNum, int failedNum, int repeatedNum, String rerepeatedURL ,String msg) {
        this.code = code;
        this.totalNum = successNum + failedNum + repeatedNum;
        this.successNum = successNum;
        this.failedNum = failedNum;
        this.repeatedNum = repeatedNum;
        this.rerepeatedURL = rerepeatedURL;
        this.msg = msg;
        this.createDate = new Date();
        return this;
    }
}
