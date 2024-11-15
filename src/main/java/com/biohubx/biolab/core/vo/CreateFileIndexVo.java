package com.biohubx.biolab.core.vo;


import com.biohubx.biolab.core.model.FileIndex;

/**
 * @author Yaping-Liu
 * @date 2024/11/14 11:40
 * @description 创建文件索引的对象
 */
public class CreateFileIndexVo {
    private String batchNo;
    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 样本唯一编号
     */
    private String uniqueNo;
    /**
     * 数据类型
     */
    private String type;
    /**
     * 数据地址
     */
    private String address;

    public FileIndex toFileIndex() {
        FileIndex fileIndex = new FileIndex();
        fileIndex.setBatchNo(batchNo);
        fileIndex.setSampleNo(sampleNo);
        fileIndex.setUniqueNo(uniqueNo);
        fileIndex.setType(type);
        fileIndex.setAddress(address);
        return fileIndex;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
