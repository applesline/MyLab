package com.biohubx.biolab.core.vo;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 17:38
 * @description 用于创建新分析任务的对象
 */
public class CreateTaskVo {
    private String workflowCode;
    private String step;
    private String uniqueNo;

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

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }
}
