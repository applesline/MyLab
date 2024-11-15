package com.biohubx.biolab.core.service;

import com.biohubx.biolab.core.dao.FileIndexDao;
import com.biohubx.biolab.core.model.Workflow;
import com.biohubx.biolab.core.model.IoTemplate;
import com.biohubx.biolab.core.model.FileIndex;
import com.biohubx.biolab.core.vo.CreateFileIndexVo;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yaping-Liu
 * @date 2024/11/12 17:39
 * @description 数据文件索引服务
 */
@Service
public class FileIndexService extends BaseService {
    public static final String RAWDATA = "rawdata";
    @Autowired
    private FileIndexDao fileIndexDao;
    @Autowired
    private WorkflowService workflowService;

    public FileIndex create(CreateFileIndexVo vo) {
        FileIndex fileIndex = vo.toFileIndex();
        if (fileIndexDao.exists(Example.of(fileIndex))) {
            throw new RuntimeException(String.format("FileIndex %s_%s_%s_%s already exist", vo.getSampleNo(), vo.getUniqueNo(), vo.getType(), vo.getAddress()));
        }
        return fileIndexDao.save(fileIndex);
    }

    public Map<String,String> fillInputData(String workflowCode, String step, String uniqueNo, List<IoTemplate> ioTemplates) {
        Workflow workflow = workflowService.getWorkflow(workflowCode, step);
        List<String> preSteps = gson.fromJson(workflow.getPreStep(), new TypeToken<List<String>>(){}.getType());
        List<FileIndex> files = new ArrayList<>();
        List<String> dataTypes = new ArrayList<>();
        // 前置流程为空则说明是流程的第一个任务
        if (ObjectUtils.isEmpty(preSteps)) {
            dataTypes.add(RAWDATA);
            files.addAll(fileIndexDao.findAll(Example.of(FileIndex.of(uniqueNo, RAWDATA))));
        } else {
            preSteps.forEach(s->{
                dataTypes.add(Workflow.toWorkflow(workflowCode, step));
            });
            files.addAll(fileIndexDao.findByUniqueNoAndTypeIn(uniqueNo,dataTypes));
        }
        if (ObjectUtils.isEmpty(files)) {
            throw new RuntimeException(String.format("Data not found for %s-%s",uniqueNo,dataTypes));
        }
        List<String> paths = new ArrayList<>();
        List<String> filePaths = files.stream().map(FileIndex::getAddress).collect(Collectors.toList());
        for (String address : filePaths) {
            paths.addAll(gson.fromJson(address , new TypeToken<List<String>>(){}.getType()));
        }
        Map<String,String> envsMap = new HashMap<>();
        for (IoTemplate env : ioTemplates) {
            for (String path : paths) {
                // 使用简单的规则查找数据
                if (path.contains(env.getFeature())) {
                    envsMap.put(env.getKey(), path);
                }
            }
        }
        String sampleNo = files.get(0).getSampleNo();
        if (envsMap.size() != ioTemplates.size()) {
            throw new RuntimeException(String.format("Insufficient environment variables for %s-%s",uniqueNo,dataTypes));
        }
        envsMap.put(SAMPLE_NO, sampleNo);
        return envsMap;
    }
}
