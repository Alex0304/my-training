package com.ch.train.service.impl;

import com.ch.train.entity.ProcessMsg;
import com.ch.train.form.ZipFileForm;

/**
 * @author DXM-0189
 * 异步任务服务，使用spring 的注解实现@Async
 */
public interface AsyncTaskService {

    void submitZipFileTask(ZipFileForm zipFileForm, ProcessMsg processMsg);
}
