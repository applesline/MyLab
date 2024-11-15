package com.biohubx.biolab.core.model;

import com.biohubx.biolab.core.constants.TaskStatus;

import javax.persistence.*;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 13:38
 * @description 定义运行时任务
 */
@Entity(name = "t_task")
public class Task {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(length = 50)
    private String sampleNo;
    @Column(length = 50)
    private String uniqueNo;
    @Column(length = 50)
    private String workflowCode;
    @Column(length = 30)
    private String step;
    @Column(length = 100)
    private String script;
    @Column(columnDefinition = "TEXT")
    private String inputData;
    @Column(length = 15)
    private String status;

    public static Task of(TaskStatus taskStatus) {
        Task task = new Task();
        task.setStatus(taskStatus.name());
        return task;
    }

    public String getTaskKey() {
        return String.format("%s_%s_%s", workflowCode, step, uniqueNo);
    }

    public Task() {
    }

    public Task(String sampleNo, String uniqueNo, String workflowCode, String step, String analysisScript, String inputData) {
        this.sampleNo = sampleNo;
        this.uniqueNo = uniqueNo;
        this.workflowCode = workflowCode;
        this.step = step;
        this.script = analysisScript;
        this.inputData = inputData;
        this.status = TaskStatus.Created.name();
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

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
