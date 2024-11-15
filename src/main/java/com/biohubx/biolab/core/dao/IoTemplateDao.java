package com.biohubx.biolab.core.dao;

import com.biohubx.biolab.core.model.IoTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yaping-Liu
 * @date 2024/11/12 17:33
 * @description 输入输出模板访问对象
 */
public interface IoTemplateDao extends JpaRepository<IoTemplate,Long> {
}
