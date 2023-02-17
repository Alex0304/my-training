package com.ch.train.service.impl;

import com.ch.train.controller.HelloController;
import com.ch.train.entity.ProcessMsg;
import com.ch.train.form.ZipFileForm;
import com.ch.train.form.ZipFileTaskForm;
import com.ch.train.utils.GZipUtils;
import com.ch.train.utils.RedisUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DXM-0189
 */
@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {
    private static final Log log = LogFactory.getLog(AsyncTaskServiceImpl.class);



    /**
     * 多线程异步处理任务
     * 1.压缩文件
     * 2.根据任务号修改redis中的任务进度，如果是最后一个文件就修改任务进度状态code
     *
     * @param zipFileForm
     */
    @Async
    @Override
    public void submitZipFileTask(ZipFileForm zipFileForm, ProcessMsg processMsg) {
        String taskId = zipFileForm.getTaskId();
        List<String> urls = zipFileForm.getUrls();
        Integer successNum = 0;
        Integer failNum = 0;
        Integer code = 0;
        for (String url : urls) {
            boolean zipSuccess = true;
            try {
                GZipUtils.compress(url, false);
            } catch (Exception e) {
                log.error("压缩文件失败，{}"+e.getMessage());
                // 压缩失败，修改失败数量
                zipSuccess = false;
            }
            if (zipSuccess) {
                successNum += 1;
            } else {
                failNum += 1;
            }
            if(successNum == urls.size()){
                code = 1;
            }
            if(failNum == urls.size()){
                code = -1;
            }
            processMsg.setSuccessNum(successNum);
            processMsg.setFailedNum(failNum);
            processMsg.setCode(code);
            RedisUtil.setProcessMsg(taskId, processMsg);
        }
    }
}
