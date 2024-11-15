package com.biohubx.biolab.core.service;

import com.biohubx.biolab.core.constants.TaskType;
import com.biohubx.biolab.core.dao.SystemConfigDao;
import com.biohubx.biolab.core.model.SystemConfig;
import com.biohubx.biolab.core.vo.CreateSystemConfigVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yaping-Liu
 * @date 2024/11/12 17:39
 * @description 分析任务依赖的环境模板服务
 */
@Service
@Order(1)
public class SystemConfigService extends BaseService implements InitializingBean  {
    @Autowired
    private SystemConfigDao systemConfigDao;

    public static final Map<String,Map<String,String>> SYSTEM_CONFIG = new HashMap<>();

    public SystemConfig create(CreateSystemConfigVo vo) {
        SystemConfig systemConfig = vo.toSystemConfig();
        if(systemConfigDao.exists(Example.of(systemConfig))) {
            throw new RuntimeException(String.format("SystemConfig %s_%s_%s already exist", vo.getType(), vo.getKey(), vo.getValue()));
        }
        return systemConfigDao.save(systemConfig);
    }

    public List<SystemConfig> list(String group) {
        List<SystemConfig> list = systemConfigDao.findAll(Example.of(SystemConfig.of(group)));
        if (ObjectUtils.isEmpty(list)) {
            log.warn("[Biolab]: Missing system config,at a minimum, [server],[baseOutputDir] and [parallel] need to be configured");
        }
        return list;
    }

    public String getConfigValue(String group, String key) {
        Map<String,String> configMap = SYSTEM_CONFIG.get(group);
        if (ObjectUtils.isEmpty(configMap)) {
            throw new RuntimeException(String.format("[SystemConfig]: Missing system config group [%s]",group));
        }
        String value = configMap.get(key);
        if (ObjectUtils.isEmpty(value)) {
            throw new RuntimeException(String.format("[SystemConfig]: Missing system config group [%s], key [%s]",group,key));
        }
        return value;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadConfig();
    }

    public void initDefaultConfig() {
        SystemConfig serverConfig = new SystemConfig(TaskType.Docker.name(), SERVER, gson.toJson(Lists.newArrayList("localhost")));
        systemConfigDao.save(serverConfig);
        log.info("[Biolab] Init docker default config server=localhost successful");
        SystemConfig parallelConfig = new SystemConfig(TaskType.Docker.name(), PARALLEL, "10");
        systemConfigDao.save(parallelConfig);
        log.info("[Biolab] Init docker default config parallel=10 successful");
        SystemConfig outputConfig = new SystemConfig(TaskType.Docker.name(), BASE_OUTPUT_DIR, gson.toJson(Lists.newArrayList("/resultdata")));
        systemConfigDao.save(outputConfig);
        log.info("[Biolab] Init docker default config baseOutputDir=/resultdata successful");
        loadConfig();
    }

    public void loadConfig() {
        List<SystemConfig> configList = list(TaskType.Docker.name());
        configList.forEach(config->{
            String group = config.getType();
            if (SYSTEM_CONFIG.containsKey(group)) {
                Map<String,String> map = SYSTEM_CONFIG.get(group);
                if (ObjectUtils.isEmpty(map)) {
                    map = new HashMap<>();
                    map.put(config.getKey(),config.getValue());
                    SYSTEM_CONFIG.put(group,map);
                } else {
                    map.putIfAbsent(config.getKey(),config.getValue());
                }
            } else {
                Map<String,String> map = new HashMap<>();
                map.put(config.getKey(),config.getValue());
                SYSTEM_CONFIG.put(group, map);
            }
        });
        Map<String,String> configMap = SYSTEM_CONFIG.get(TaskType.Docker.name());
        if (ObjectUtils.isEmpty(configMap)) {
            log.info("[Biolab] Due to empty config, start init a default system config");
            initDefaultConfig();
        }
    }
}
