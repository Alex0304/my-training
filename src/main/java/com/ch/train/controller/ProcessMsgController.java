package com.ch.train.controller;


import com.ch.train.entity.ProcessMsg;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.ZipFileForm;
import com.ch.train.service.ProcessMsgService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文件压缩批量任务处理
 *
 * @author chenhuan
 * @since 2023-02-15 10:41:40
 */
@RestController
@RequestMapping("task")
public class ProcessMsgController {

    @Resource
    private ProcessMsgService processMsgService;


    /**
     * 提交压缩任务
     * 返回任务id
     * @param zipFileForm
     * @return
     */
    @PostMapping("/zip")
    public ResponseEntity<String> submitZipTask(@RequestBody ZipFileForm zipFileForm) throws BusinessException {
        return ResponseEntity.ok(this.processMsgService.submitZipTask(zipFileForm));
    }

    @PostMapping("/queryProcessById")
    public ResponseEntity<ProcessMsg> queryProcessById(String taskId) {
        return ResponseEntity.ok(this.processMsgService.queryProcessById(taskId));
    }
}
