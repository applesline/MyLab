package com.biohubx.biolab.core.controller;

import com.biohubx.biolab.core.service.SystemConfigService;
import com.biohubx.biolab.core.vo.CreateSystemConfigVo;
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
@RequestMapping("/system")
public class SystemConfigController {
    @Autowired
    private SystemConfigService systemConfigService;

    @RequestMapping("config")
    public void config(@RequestBody CreateSystemConfigVo vo) {
        systemConfigService.create(vo);
    }

}
