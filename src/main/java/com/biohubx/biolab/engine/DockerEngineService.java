package com.biohubx.biolab.engine;

import com.biohubx.biolab.core.constants.TaskType;
import com.biohubx.biolab.core.model.Task;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author : liuyaping
 * @date 2022/11/22 9:23
 */
@Service
public class DockerEngineService extends AbstractEngineService implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(DockerEngineService.class);
    private static final Map<String,DockerClient> DOCKER_CLIENT_MAP = new ConcurrentHashMap<>();

    /**
     * 启动执行引擎。
     *
     * @return 容器ID
     */
    @Override
    public String start(String server, Task task) throws Exception {
        synchronized (server) {
            DockerClient dockerClient = buildDockerClient(server);
            List<String> taskIds = getRunningTask(server);
            if (!withinParallelTask(server,taskIds)) {
                return "";
            }
            // 检查镜像是否存在， 不存在则拉取镜像
            checkAndPullImage(dockerClient,task.getScript());
            // 运行镜像
            CreateContainerResponse container = buildCreateContainerCmd(dockerClient,task);
            dockerClient.startContainerCmd(container.getId()).exec();
            return container.getId();
        }
    }

    @Override
    public boolean withinParallel(String server) {
        List<String> containers = getRunningTask(server);
        String value = systemConfigService.getConfigValue(getTaskType().name(),PARALLEL);
        log.info("[Biolab] server {} has {} tasks in running, max parallel is {}",server,containers.size(),value);
        return containers.size() <= Integer.parseInt(value);
    }

    private List<String> getRunningTask(String server) {
        List<String> taskIds = RUNNING_TASK_CACHE.getIfPresent(server);
        if (CollectionUtils.isEmpty(taskIds)) {
            DockerClient dockerClient = buildDockerClient(server);
            taskIds = dockerClient.listContainersCmd().withStatusFilter(Lists.newArrayList("running")).exec().stream().map(Container::getId).collect(Collectors.toList());
            RUNNING_TASK_CACHE.put(server,taskIds);
        }
        return taskIds;
    }

    @Override
    protected List<String> getLowestUsageServer() {
        Map<String,List<String>> map = RUNNING_TASK_CACHE.asMap();
        Map<Integer,String> sortedMap = new TreeMap<>();
        for (String server : getServers()) {
            int size = map.get(server).size();
            log.info("[Biolab] {} current running tasks {}", server, size);
            sortedMap.put(size, server);
        }
        return new ArrayList<>(sortedMap.values());
    }

    private boolean withinParallelTask(String server,List<String> tasks) {
        String value = systemConfigService.getConfigValue(getTaskType().name(),PARALLEL);
        log.info("[Biolab] server {} has {} tasks in running, max parallel is {}",server,tasks.size(),value);
        return tasks.size() <= Integer.parseInt(value);
    }


    public void checkAndPullImage(DockerClient client, String image)  {
        boolean existImage = false;
        List<Image> images = client.listImagesCmd().exec();
        for (Image img : images) {
            for (String repoTag : img.getRepoTags()) {
                if (repoTag.equalsIgnoreCase(image)) {
                    existImage = true;
                    break;
                }
            }
            if (existImage) break;
        }
        if (!existImage) {
            try {
                client.pullImageCmd(image).exec(new PullImageResultCallback()).awaitCompletion();
            } catch (Exception e) {
                throw new RuntimeException(String.format("[Biolab] Pulling the image [%s] timeout", image));
            }
        }
    }

    /**
     * 构建docker启动脚本。
     *
     * @return
     */

    private List<String> buildDockerEnv(Task task) {
        List<String> envs = new ArrayList<>();
        Map<String,String> params = gson.fromJson(task.getInputData(), new TypeToken<Map<String,String>>(){}.getType());
        for (Map.Entry<String,String> entry : params.entrySet()) {
            envs.add(String.format("%s=%s" , entry.getKey() , entry.getValue()));
        }
        return envs;
    }

    /**
     * 给定IP及端口构建dockerClient。
     *
     * @param server ip:port
     * @return dockerClient
     */
    private DockerClient buildDockerClient(String server) {
        String address = String.format("tcp://%s:2375",server);
        DockerClient dockerClient = DOCKER_CLIENT_MAP.get(address);
        if (ObjectUtils.isEmpty(dockerClient)) {
            DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(address)
//                .withDockerTlsVerify(true)
//                .withDockerCertPath("D:\\dockercert")
                    .build();
            dockerClient = DockerClientBuilder.getInstance(config).build();
            DOCKER_CLIENT_MAP.putIfAbsent(address,dockerClient);
        }
        return dockerClient;
    }

    private CreateContainerResponse buildCreateContainerCmd(DockerClient dockerClient, Task task) {
        List<String> envs = buildDockerEnv(task);
        HostConfig hostConfig = HostConfig.newHostConfig()
                .withPrivileged(true)
                .withAutoRemove(true);
//                .withDns(dns);

//        configBind(hostConfig,taskInfo);
        return dockerClient.createContainerCmd(task.getScript())
                .withHostConfig(hostConfig)
                .withEnv(envs)
                .exec();
    }


    @Override
    public List<String> getServers() {
        String value = systemConfigService.getConfigValue(getTaskType().name(),SERVER);
        return gson.fromJson(value, new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.Docker;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String server : getServers()) {
            log.info("[Biolab] init {} running task to 0", server);
            RUNNING_TASK_CACHE.put(server, new ArrayList<>());
        }
    }
}
