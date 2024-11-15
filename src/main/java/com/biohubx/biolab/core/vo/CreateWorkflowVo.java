package com.biohubx.biolab.core.vo;

import com.biohubx.biolab.core.model.Workflow;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 18:00
 * @description 创建数据分析工作流的对象
 */
public class CreateWorkflowVo {
    private String workflowName;
    private String workflowCode;
    private String step;
    private String preStep;

    public Workflow toWorkflow() {
        Workflow workflow = new Workflow();
        workflow.setWorkflowName(workflowName);
        workflow.setWorkflowCode(workflowCode);
        workflow.setStep(step);
        workflow.setPreStep(preStep);
        return workflow;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowCode() {
        return workflowCode;
    }

    public void setWorkflowCode(String workflowCode) {
        this.workflowCode = workflowCode;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getPreStep() {
        return preStep;
    }

    public void setPreStep(String preStep) {
        this.preStep = preStep;
    }
}
