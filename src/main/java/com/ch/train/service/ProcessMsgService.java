package com.ch.train.service;

import com.ch.train.entity.ProcessMsg;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.ZipFileForm;

/**
 * @author DXM-0189
 */
public interface ProcessMsgService {


    /**
     * 提交文件压缩任务
     * @param zipFileForm
     * @return
     */
    String submitZipTask(ZipFileForm zipFileForm) throws BusinessException;

    /**
     * 查询压缩任务进度
     * @param taskId
     * @return
     */
    ProcessMsg queryProcessById(String taskId);
}
