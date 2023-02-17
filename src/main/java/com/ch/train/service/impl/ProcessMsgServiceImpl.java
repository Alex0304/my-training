package com.ch.train.service.impl;

import com.ch.train.entity.ProcessMsg;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.ZipFileForm;
import com.ch.train.service.ProcessMsgService;
import com.ch.train.utils.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author DXM-0189
 */
@Service
public class ProcessMsgServiceImpl implements ProcessMsgService {

    @Resource
    private AsyncTaskService asyncTaskService;


    @Override
    public String submitZipTask(ZipFileForm zipFileForm) throws BusinessException {
        List<String> urls = zipFileForm.getUrls();
        if(CollectionUtils.isEmpty(urls)){
            throw new  BusinessException("压缩文件地址不能为空");
        }
        String taskId = UUID.randomUUID().toString();
        ProcessMsg processMsg = new ProcessMsg(0,0,0,urls.size());
        RedisUtil.setProcessMsg(taskId,processMsg,1800);
        asyncTaskService.submitZipFileTask(zipFileForm.setTaskId(taskId),processMsg);
        return taskId;
    }


    /**
     * @param taskId
     * @return
     */
    @Override
    public ProcessMsg queryProcessById(String taskId) {
        return RedisUtil.getProcessMsg(taskId);
    }
}
