package com.biohubx.biolab.core.constants;

/**
 * @Author : liuyaping
 * @date 2023/11/9 11:09
 */
public class CacheKey {

    /**
     * 用于存储服务器正在运行的docker容器ID  set结构
     * 例如：
     *  key     server#10.10.108.26
     *  value：  cb39b7239614
     *           db39b7239614
     */
    public static final String SERVER_KEY = "server#%s";

    /**
     * 暂时不使用
     *
     * 用于存储容器ID在哪台服务器上运行   string
     * 例如：
     *  key  containerId#cb39b7239614
     *  value 10.10.108.26
     */
    public static final String CONTAINER_KEY = "containerId#%s";

    /**
     *  存储业务数据对应的容器ID
     *  例如：
     *  key  Analysis#MN04986_E1_L1_S1#Singlestrain_datafilter
     *  value cb39b7239614
     */
    public static final String RUNNING_TASK_KEY = "Analysis#%s#%s";

    public static String getServerKey(String server) {
        return String.format(SERVER_KEY,server);
    }

    public static String getContainerKey(String containerId) {
        return String.format(CONTAINER_KEY,containerId);
    }

//    public String getRunningTaskKey(String uniqueNo, AnalysisFlow analysisFlow) {
//        return String.format(RUNNING_TASK_KEY,uniqueNo,analysisFlow.name());
//    }

}
