package com.biohubx.biolab.core.vo;

import com.biohubx.biolab.core.model.IoTemplate;

/**
 * @author Yaping-Liu
 * @date 2024/11/14 10:15
 * @description 入参模板对象
 */
public class CreateIoTemplateVo {
    private String workflowCode;
    private String step;
    private String type;
    private String key;
    private String feature;

    public IoTemplate toInputTemplate() {
        IoTemplate ioTemplate = new IoTemplate();
        ioTemplate.setWorkflowCode(workflowCode);
        ioTemplate.setStep(step);
        ioTemplate.setType(type);
        ioTemplate.setKey(key);
        ioTemplate.setFeature(feature);
        return ioTemplate;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
