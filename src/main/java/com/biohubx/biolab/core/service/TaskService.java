package com.biohubx.biolab.core.service;

import com.biohubx.biolab.core.constants.IoType;
import com.biohubx.biolab.core.dao.TaskDao;
import com.biohubx.biolab.core.model.AnalysisScript;
import com.biohubx.biolab.core.model.IoTemplate;
import com.biohubx.biolab.core.model.Task;
import com.biohubx.biolab.core.constants.TaskStatus;
import com.biohubx.biolab.core.vo.CreateTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 13:57
 * @description 数据分析服务
 */
@Service
public class TaskService extends BaseService {
    @Autowired
    private AnalysisScriptService analysisScriptService;
    @Autowired
    private IoTemplateService ioTemplateService;
    @Autowired
    private FileIndexService fileIndexService;
    @Autowired
    private TaskDao taskDao;

    public static final PriorityQueue<Task> PRIORITY_TASK_QUEUE = new PriorityQueue<>((t1, t2) -> Long.compare(t2.getId(), t1.getId()));

    public Task createTask(CreateTaskVo vo) {
        return createTask(vo.getWorkflowCode(),vo.getStep(),vo.getUniqueNo());
    }

    public Task createTask(String workflowCode, String step, String uniqueNo) {
        final AnalysisScript analysisScript = analysisScriptService.getAnalysisScript(workflowCode,step);
        List<IoTemplate> ioTemplates = ioTemplateService.list(workflowCode, step, IoType.Input);
        Map<String,String> params = fileIndexService.fillInputData(workflowCode,step,uniqueNo, ioTemplates);
        Task task = new Task(params.get(SAMPLE_NO),uniqueNo,workflowCode,step,analysisScript.getScript(),gson.toJson(params));
        if (taskDao.exists(Example.of(task))) {
            throw new RuntimeException(String.format("Task %s_%s_%s already exist", task.getWorkflowCode(), task.getStep(), uniqueNo));
        }
        return taskDao.save(task);
    }

    public List<Task> listCreatedTask() {
        List<Task> tasks;
        if (ObjectUtils.isEmpty(PRIORITY_TASK_QUEUE)) {
            tasks = taskDao.findAll(Example.of(Task.of(TaskStatus.Created)));
            PRIORITY_TASK_QUEUE.addAll(tasks);

        }
        List<Task> topTasks = new ArrayList<>();
        for (int i = 0; i < Math.min(PRIORITY_TASK_QUEUE.size(), 10); i++) {
            Task task = PRIORITY_TASK_QUEUE.poll();
            log.info("[Biolab] Loading Task {}_{}_{} ,status changed from Created to Pending ", task.getWorkflowCode(), task.getStep(), task.getUniqueNo());
            this.createdToPending(task);
            topTasks.add(task);
        }
        taskDao.saveAll(topTasks);
        return topTasks;
    }

    public Task createdToPending(Task task) {
        task.setStatus(TaskStatus.Pending.name());
        PRIORITY_TASK_QUEUE.remove(task);
        return taskDao.save(task);
    }

    public Task pendingToCreated(Task task) {
        task.setStatus(TaskStatus.Created.name());
        return taskDao.save(task);
    }

    public Task pendingToRunning(Task task) {
        task.setStatus(TaskStatus.Running.name());
        PRIORITY_TASK_QUEUE.remove(task);
        return taskDao.save(task);
    }
    public Task pendingToFailure(Task task) {
        task.setStatus(TaskStatus.Failure.name());
        PRIORITY_TASK_QUEUE.remove(task);
        return taskDao.save(task);
    }

}
