package com.biohubx.biolab.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author Yaping-Liu
 * @date 2024/11/14 15:09
 * @description 系统配置
 */
@Entity(name = "t_system_config")
public class SystemConfig {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String type;
    @Column(length = 50)
    private String key;
    @Column(length = 1000)
    private String value;

    public SystemConfig() {
    }

    public SystemConfig(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public static SystemConfig of(String type) {
        SystemConfig config = new SystemConfig();
        config.setType(type);
        return config;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
