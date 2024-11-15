package com.biohubx.biolab.core.dao;

import com.biohubx.biolab.core.model.FileIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Yaping-Liu
 * @date 2024/11/12 17:33
 * @description 索引数据访问对象
 */
public interface FileIndexDao extends JpaRepository<FileIndex,Long> {
    List<FileIndex> findByUniqueNoAndTypeIn(String uniqueNo,List<String> types);
}
