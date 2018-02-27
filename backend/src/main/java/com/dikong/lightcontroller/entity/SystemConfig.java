package com.dikong.lightcontroller.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Table;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月24日上午10:49
 * @see </P>
 */
@Table(name = "system")
public class SystemConfig {

    //普通类型值
    public static final Integer CHAR = 1;
    //文件类型值
    public static final Integer FILE = 2;

    private Long id;
    /**
     * 设置值
     */
    private String value;

    /**
     * 值类型
     */
    private Integer valueType;
    /**
     * 系统设置类型编号，一个递增数子
     */
    private Integer typeId;
    /**
     * 系统设置值
     */
    private String typeValue;

    private MultipartFile file;

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
