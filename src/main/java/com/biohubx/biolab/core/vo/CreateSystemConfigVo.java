package com.biohubx.biolab.core.vo;


import com.biohubx.biolab.core.model.SystemConfig;

/**
 * @author Yaping-Liu
 * @date 2024/11/14 15:25
 * @description 系统配置对象
 */
public class CreateSystemConfigVo {
    private String type;
    private String key;
    private String value;

    public SystemConfig toSystemConfig() {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setType(type);
        systemConfig.setKey(key);
        systemConfig.setValue(value);
        return systemConfig;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
