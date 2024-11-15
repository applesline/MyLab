package com.biohubx.biolab.core.constants;

/**
 * @Author : liuyaping
 * @date 2023/3/13 10:15
 */
public enum TaskType {
    Docker;
    public static TaskType convert(String type) {
        for (TaskType value : TaskType.values()) {
            if (value.name().equalsIgnoreCase(type)) {
                return value;
            }
        }
        return null;
    }
}
