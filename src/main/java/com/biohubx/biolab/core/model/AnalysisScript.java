package com.biohubx.biolab.core.model;

import javax.persistence.*;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 11:42
 * @description 数据分析算法
 */
@Entity(name = "t_analysis_script")
public class AnalysisScript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String workflowCode;
    @Column(length = 30)
    private String step;
    @Column(length = 30)
    private String type;
    @Column(length = 100)
    private String script;

    public static AnalysisScript of(String workflowCode,String step) {
        return new AnalysisScript(workflowCode,step);
    }

    public AnalysisScript() {
    }

    public AnalysisScript(String workflowCode, String step) {
        this.workflowCode = workflowCode;
        this.step = step;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
