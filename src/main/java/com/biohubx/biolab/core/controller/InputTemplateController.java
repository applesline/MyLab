package com.biohubx.biolab.core.controller;

import com.biohubx.biolab.core.service.IoTemplateService;
import com.biohubx.biolab.core.vo.CreateIoTemplateVo;
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
@RequestMapping("/input")
public class InputTemplateController {
    @Autowired
    private IoTemplateService ioTemplateService;

    @RequestMapping("create")
    public void createTask(@RequestBody CreateIoTemplateVo vo) {
        ioTemplateService.create(vo);
    }

}
