package com.biohubx.biolab.engine;

import com.biohubx.biolab.core.model.Task;
import com.biohubx.biolab.core.service.BaseService;
import com.biohubx.biolab.core.service.SystemConfigService;
import com.biohubx.biolab.core.service.TaskService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @Author : liuyaping
 * @date 2022/11/15 13:21
 */
public abstract class AbstractEngineService extends BaseService implements IEngineService {
    public final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected SystemConfigService systemConfigService;
    @Autowired
    private TaskService taskService;
    @Autowired
    protected Gson gson;

    /**
     * key = server
     * value = taskId
     */
    protected static final Cache<String, List<String>> RUNNING_TASK_CACHE = CacheBuilder.newBuilder().build();

    @Override
    public String startEngine(Task task) throws Exception {
        for (String server : getLowestUsageServer()) {
            String containerId = start(server, task);
            if (ObjectUtils.isEmpty(containerId)) {
                log.info("[Biolab] Server {} can't start task {}_{}_{}, status from Pending changed to Created", server, task.getWorkflowCode(), task.getStep(), task.getUniqueNo());
                taskService.pendingToCreated(task);
            } else {
                log.info("[Biolab] Server {} start a running task {} for {}_{}_{}", server, containerId, task.getWorkflowCode(), task.getStep(), task.getUniqueNo());
                RUNNING_TASK_CACHE.getIfPresent(server).add(containerId);
                taskService.pendingToRunning(task);
            }
            return containerId;
        }
        return "";
    }

    /**
     * 在指定的服务器上跑任务。
     *
     * @param server 服务器  eg:192.168.2.71:2375
     * @return
     */
    public abstract String start(String server,Task task) throws Exception;


    /**
     * 没有指定服务器则选择综合资源利用率最低的服务器
     *
     * @return String
     */
    protected abstract List<String> getLowestUsageServer();

    protected abstract boolean withinParallel(String server);

}