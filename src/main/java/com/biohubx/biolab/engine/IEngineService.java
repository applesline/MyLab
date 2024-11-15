package com.biohubx.biolab.engine;

import com.biohubx.biolab.core.constants.TaskType;
import com.biohubx.biolab.core.model.Task;

import java.util.List;

/**
 * @Author : liuyaping
 * @date 2022/11/15 13:21
 */
public interface IEngineService {

    /**
     * 启动执行引擎。
     *
     * @return
     */
    String startEngine(Task task) throws Exception;

    List<String> getServers();

    /**
     * 任务类型
     *
     * @return
     */
    TaskType getTaskType();
}