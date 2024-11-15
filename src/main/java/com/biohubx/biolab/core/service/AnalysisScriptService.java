package com.biohubx.biolab.core.service;

import com.biohubx.biolab.core.dao.AnalysisScriptDao;
import com.biohubx.biolab.core.model.AnalysisScript;
import com.biohubx.biolab.core.vo.CreateAnalysisScriptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @author Yaping-Liu
 * @date 2024/11/12 17:39
 * @description 数据分析脚本服务
 */
@Service
public class AnalysisScriptService {
    @Autowired
    private AnalysisScriptDao analysisScriptDao;
    public AnalysisScript create(CreateAnalysisScriptVo vo) {
        AnalysisScript analysisScript = vo.toAnalysisScript();
        if(analysisScriptDao.exists(Example.of(analysisScript))) {
            throw new RuntimeException(String.format("AnalysisScript %s_%s %s already exist", vo.getWorkflowCode(), vo.getStep(), vo.getScript()));
        }
        return analysisScriptDao.save(analysisScript);
    }
    public AnalysisScript getAnalysisScript(String workflowCode,String step) {
        return analysisScriptDao.findOne(Example.of(AnalysisScript.of(workflowCode,step))).orElseThrow(()->new RuntimeException(String.format("Invalid workflowCode [%s] and step [%s]",workflowCode,step)));
    }
}
