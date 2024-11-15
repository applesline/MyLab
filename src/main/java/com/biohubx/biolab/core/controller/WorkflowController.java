package com.biohubx.biolab.core.controller;

import com.biohubx.biolab.core.service.WorkflowService;
import com.biohubx.biolab.core.vo.CreateWorkflowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 17:34
 * @description 分析任务控制器
 */
@RestController
@RequestMapping("/workflow")
public class WorkflowController {
    @Autowired
    private WorkflowService workflowService;

    @RequestMapping("create")
    public void createTask(@RequestBody CreateWorkflowVo vo) {
        workflowService.create(vo);
    }

}
