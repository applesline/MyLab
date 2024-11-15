package com.biohubx.biolab.core.dao;

import com.biohubx.biolab.core.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yaping-Liu
 * @date 2024/11/12 17:33
 * @description 索引数据访问对象
 */
public interface TaskDao extends JpaRepository<Task,Long> {
}
