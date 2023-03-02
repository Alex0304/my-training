package com.ch.train.controller;


import com.ch.train.entity.ProcessMsg;
import com.ch.train.exception.BusinessException;
import com.ch.train.form.ZipFileForm;
import com.ch.train.service.ProcessMsgService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

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
     * @param url
     * @return
     */
    @PostMapping("/zip")
    public ResponseEntity<String> submitZipTask(@RequestParam String url) throws BusinessException {
        ZipFileForm zipFileForm = new ZipFileForm().setUrls(Arrays.asList(url.split(",")));
        return ResponseEntity.ok(this.processMsgService.submitZipTask(zipFileForm));
    }

    @GetMapping("/queryProcessById")
    public ResponseEntity<ProcessMsg> queryProcessById(String taskId) {
        return ResponseEntity.ok(this.processMsgService.queryProcessById(taskId));
    }

}
