package com.biohubx.biolab.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 13:46
 * @description 运行时依赖的环境变量模板，需要预先定义输入与输出的模板（参数及排序，约定最后一个为输出地址）
 */
@Entity(name = "t_io_template")
public class IoTemplate {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(length = 50)
    private String workflowCode;
    @Column(length = 30)
    private String step;
    @Column(length = 10)
    private String type;
    @Column(length = 50)
    private String key;
    @Column(length = 30)
    private String feature;
    


    public static IoTemplate of(String workflowCode, String step, String ioType) {
        return new IoTemplate(workflowCode,step, ioType);
    }

     public IoTemplate() {
    }

    public IoTemplate(String workflowCode, String step, String ioType) {
        this.workflowCode = workflowCode;
        this.step = step;
        this.type = ioType;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
