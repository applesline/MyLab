package com.biohubx.biolab.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;

/**
 * @author Yaping-Liu
 * @date 2024/11/15 16:01
 * @description 系统初始化器
 */
@Configuration
@ConfigurationProperties("spring.datasource")
public class SystemInitializer {
    public static final Logger log = LoggerFactory.getLogger(SystemInitializer.class);

    @Bean
    public DataSource dataSource() {
        String userDir = System.getProperty("user.dir");
        String url = String.format("jdbc:sqlite:%s%sBiolab", userDir, File.separator);
        log.info("[Biolab] Sqlite database path: [{}{}biolab]", userDir,File.separator);
        return DataSourceBuilder
                .create()
                .url(url)
                .driverClassName("org.sqlite.JDBC")
                .build();
    }

}
