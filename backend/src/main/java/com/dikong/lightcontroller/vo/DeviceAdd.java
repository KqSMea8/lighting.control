package com.dikong.lightcontroller.vo;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月21日下午9:24
 * @see
 *      </P>
 */
public class DeviceAdd {
    //用于生成主键,不需要上传
    private Long id;
    // 对外id
    private String externalId;
    // dtu id
    private Long dtuId;
    // 设备名称
    private String name;
    // 设备编号,不能重复
    private String code;
    // 通讯点表文件
    private String model;

    //创建人
    private Integer createBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Long getDtuId() {
        return dtuId;
    }

    public void setDtuId(Long dtuId) {
        this.dtuId = dtuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
}
