package com.biohubx.biolab.core.controller;

import com.biohubx.biolab.core.service.AnalysisScriptService;
import com.biohubx.biolab.core.vo.CreateAnalysisScriptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 17:34
 * @description 分析任务控制器
 */
@RestController
@RequestMapping("/script")
public class AnalysisScriptController {
    @Autowired
    private AnalysisScriptService analysisScriptService;

    @RequestMapping("create")
    public void create(@RequestBody CreateAnalysisScriptVo vo) {
        analysisScriptService.create(vo);
    }

}
