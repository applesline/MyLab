package com.biohubx.biolab.core.controller;

import com.biohubx.biolab.core.model.FileIndex;
import com.biohubx.biolab.core.service.FileIndexService;
import com.biohubx.biolab.core.vo.CreateFileIndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author Yaping-Liu
 * @date 2024/11/12 17:45
 * @description 数据索引控制器
 */

@RestController
@RequestMapping("/index")
public class FileIndexController {

    @Autowired
    private FileIndexService fileIndexService;

    @RequestMapping("/create")
    public FileIndex create(@RequestBody CreateFileIndexVo vo) {
        return fileIndexService.create(vo);
    }

}
