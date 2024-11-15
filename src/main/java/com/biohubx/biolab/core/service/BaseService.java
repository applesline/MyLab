package com.biohubx.biolab.core.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yaping-Liu
 * @date 2024/11/13 16:17
 * @description 基础服务
 */
public class BaseService {
    protected static final String SAMPLE_NO = "sampleNo";
    protected static final String SERVER = "server";
    protected static final String PARALLEL = "parallel";
    protected static final String BASE_OUTPUT_DIR = "baseOutputDir";
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    protected Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .create();
}
