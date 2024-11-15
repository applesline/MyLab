package com.biohubx.biolab.core.vo;

import com.biohubx.biolab.core.model.AnalysisScript;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 18:23
 * @description 创建分析脚本的对象
 */
public class CreateAnalysisScriptVo {
    private String workflowCode;
    private String step;
    private String type;
    private String script;

    public AnalysisScript toAnalysisScript() {
        AnalysisScript analysisScript = new AnalysisScript();
        analysisScript.setWorkflowCode(workflowCode);
        analysisScript.setStep(step);
        analysisScript.setType(type);
        analysisScript.setScript(script);
        return analysisScript;
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
