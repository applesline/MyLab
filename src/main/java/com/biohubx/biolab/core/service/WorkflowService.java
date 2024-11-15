package com.biohubx.biolab.core.service;

import com.biohubx.biolab.core.dao.WorkflowDao;
import com.biohubx.biolab.core.model.Workflow;
import com.biohubx.biolab.core.vo.CreateWorkflowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @author Yaping-Liu
 * @date 2024/11/12 17:39
 * @description 数据工作流服务
 */
@Service
public class WorkflowService {
    @Autowired
    private WorkflowDao workflowDao;

    public Workflow create(CreateWorkflowVo vo) {
        Workflow workflow = vo.toWorkflow();
        if(workflowDao.exists(Example.of(workflow))) {
            throw new RuntimeException(String.format("Workflow %s_%s already exist", vo.getWorkflowCode(), vo.getStep()));
        }
        return workflowDao.save(workflow);
    }

    public Workflow getWorkflow(String workflowCode,String step) {
        return workflowDao.findOne(Example.of(Workflow.of(workflowCode,step))).orElseThrow(()-> new RuntimeException(String.format("Workflow %s_%s not exist",workflowCode,step)));
    }
}
