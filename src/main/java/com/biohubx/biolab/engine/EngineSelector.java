package com.biohubx.biolab.engine;

import com.biohubx.biolab.core.constants.TaskType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 引擎初始化器。
 *
 * @Author : liuyaping
 * @date 2022/9/13 11:29
 */
@Service
public class EngineSelector implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;
    private static final Map<TaskType, AbstractEngineService> SERVICE_MAP = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public AbstractEngineService getService(String engine) {
        TaskType taskType = TaskType.convert(engine);
        return SERVICE_MAP.getOrDefault(taskType,null);
    }

    public AbstractEngineService getService(TaskType taskType) {
        return SERVICE_MAP.getOrDefault(taskType,null);
    }

    public void afterPropertiesSet() throws Exception {
        Map<String, AbstractEngineService> services = applicationContext.getBeansOfType(AbstractEngineService.class);
        services.values().forEach(service->{
            SERVICE_MAP.putIfAbsent(service.getTaskType(),service);
        });
    }

}
