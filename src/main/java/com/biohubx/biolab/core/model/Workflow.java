package com.biohubx.biolab.core.model;

import javax.persistence.*;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 11:50
 * @description 数据分析工作流,定义流程的依赖关系
 */
@Entity(name = "t_workflow")
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String workflowName;
    @Column(length = 50)
    private String workflowCode;
    @Column(length = 30)
    private String step;
    @Column(length = 600)
    private String preStep;

    public static Workflow of(String workflowCode,String step) {
        return new Workflow(workflowCode,step);
    }

    public static String toWorkflow(String workflowCode,String step) {
        return String.format("%s_%s",workflowCode,step);
    }

    public Workflow() {
    }

    public Workflow(String workflowCode, String currentStep) {
        this.workflowCode = workflowCode;
        this.step = currentStep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
