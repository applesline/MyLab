package com.biohubx.biolab.diapatch;

import com.biohubx.biolab.core.constants.TaskType;
import com.biohubx.biolab.core.model.Task;
import com.biohubx.biolab.core.service.BaseService;
import com.biohubx.biolab.core.service.TaskService;
import com.biohubx.biolab.engine.EngineSelector;
import com.biohubx.biolab.engine.IEngineService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yaping-Liu
 * @date 2024/11/14 13:35
 * @description 任务调度服务
 */
@Service
public class TaskDispatchService extends BaseService implements ApplicationRunner {
    public static final int MAX_RETRY_TIMES = 5;
    @Autowired
    private TaskService taskService;
    @Autowired
    private EngineSelector engineSelector;
    /**
     * 同一个任务失败次数过多（限定重试MAX_RETRY_TIMES次）时需主动删除，避免正常任务被锁死
     */
    private final Map<String,Integer> RETRY_TASKS_MONITOR = new ConcurrentHashMap<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        IEngineService engineService = engineSelector.getService(TaskType.Docker);
        Thread t = new Thread(()->{
            while (true) {
                List<Task> tasks = taskService.listCreatedTask();
                for (Task task : tasks) {
                    if (RETRY_TASKS_MONITOR.containsKey(task.getTaskKey())) {
                        int times = RETRY_TASKS_MONITOR.get(task.getTaskKey()) + 1;
                        RETRY_TASKS_MONITOR.put(task.getTaskKey(),times);
                        // 如果一个任务被重试了MAX_RETRY_TIMES次依然还停留在RETRY_TASK_MAP容器中，说明此任务大概率是存在问题的，此时需要剔除掉该任务
                        if (times >= MAX_RETRY_TIMES) {
                            log.info("[Biolab] Task {}_{}_{} from Pending changed to Failure due to retry 5 times", task.getWorkflowCode(), task.getStep(), task.getUniqueNo());
                            taskService.pendingToFailure(task);
                        }
                    } else {
                        RETRY_TASKS_MONITOR.put(task.getTaskKey(),1);
                    }
                    try {
                        log.info("[Biolab] Auto start task {}_{}_{} ",task.getWorkflowCode(), task.getStep(), task.getUniqueNo());
                        engineService.startEngine(task);
                    } catch (Exception e) {
                        log.error("[Biolab] Failure to start task {}_{}_{} \n",task.getWorkflowCode(), task.getStep(), task.getUniqueNo(), e.fillInStackTrace());
                        if (RETRY_TASKS_MONITOR.get(task.getTaskKey()) < MAX_RETRY_TIMES) {
                            log.info("[Biolab] Task {}_{}_{} from Pending changed to Created with retry {} times", task.getWorkflowCode(), task.getStep(), task.getUniqueNo(), RETRY_TASKS_MONITOR.get(task.getTaskKey()));
                            taskService.pendingToCreated(task);
                        }
                    }
                }
            }
        });
        t.start();
    }
}
