package com.biohubx.biolab.core.model;


import javax.persistence.*;

/**
 * @author Yaping-Liu
 * @date 2024/11/12 17:17
 * @description 数据文件索引
 */
@Entity(name = "t_file_index")
public class FileIndex {
    /**
     * 数据记录唯一编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    /**
     * 数据批次号，建议使用“年月日”格式，例如：20241112
     */
    @Column(length = 50)
    private String batchNo;
    /**
     * 样本编号
     */
    @Column(length = 50)
    private String sampleNo;
    /**
     * 样本唯一编号
     */
    @Column(length = 50)
    private String uniqueNo;
    /**
     * 数据类型
     */
    @Column(length = 100)
    private String type;
    @Column(length = 300)
    private String note;
    /**
     * 数据地址
     */
    @Column(columnDefinition = "TEXT")
    private String address;

    public static FileIndex of(String uniqueNo ,String dataType) {
        FileIndex fileIndex = new FileIndex();
        fileIndex.setType(dataType);
        fileIndex.setUniqueNo(uniqueNo);
        return fileIndex;
    }

    public FileIndex() {
    }



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
