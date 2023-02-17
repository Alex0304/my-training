package com.ch.train.form;

import java.io.Serializable;

/**
 * @author DXM-0189
 */
public class ZipFileTaskForm implements Serializable {

    private String taskId;

    private String fileUrl;

    public ZipFileTaskForm() {
    }

    public ZipFileTaskForm(String taskId, String fileUrl) {
        this.taskId = taskId;
        this.fileUrl = fileUrl;
    }

    public String getTaskId() {
        return taskId;
    }

    public ZipFileTaskForm setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public ZipFileTaskForm setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }
}
