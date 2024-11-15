package com.biohubx.biolab.core.service;

import com.biohubx.biolab.core.constants.IoType;
import com.biohubx.biolab.core.model.IoTemplate;
import com.biohubx.biolab.core.vo.CreateIoTemplateVo;
import com.biohubx.biolab.core.dao.IoTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author Yaping-Liu
 * @date 2024/11/12 17:39
 * @description 分析任务依赖的环境模板服务
 */
@Service
public class IoTemplateService {
    @Autowired
    private IoTemplateDao ioTemplateDao;

    public IoTemplate create(CreateIoTemplateVo vo) {
        IoTemplate ioTemplate = vo.toInputTemplate();
        if(ioTemplateDao.exists(Example.of(ioTemplate))) {
            throw new RuntimeException(String.format("Workflow %s_%s_%s already exist", vo.getWorkflowCode(), vo.getStep(), vo.getKey()));
        }
        return ioTemplateDao.save(ioTemplate);
    }

    public List<IoTemplate> list(String workflowCode, String step, IoType ioType) {
        List<IoTemplate> envs = ioTemplateDao.findAll(Example.of(IoTemplate.of(workflowCode,step, ioType.name())));
        if (ObjectUtils.isEmpty(envs)) {
            throw new RuntimeException(String.format("No environment variables are configured for %s_%s_%s ",workflowCode,step,ioType));
        }
        return envs;
    }
}
