package com.ch.train.form;


import java.util.List;

/**
 * @author DXM-0189
 */
public class ZipFileForm extends DataAuthForm{


    /**
     * 文件路径
     */
    private List<String> urls;

    String taskId;

    public String getTaskId() {
        return taskId;
    }

    public ZipFileForm setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public List<String> getUrls() {
        return urls;
    }

    public ZipFileForm setUrls(List<String> urls) {
        this.urls = urls;
        return this;
    }
}
