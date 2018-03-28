package com.dikong.lightcontroller.entity;

import javax.persistence.Id;
import java.util.Date;

/**
 * 设备表 串口设备
 * 
 * @author lengrongfu
 * @date 2018年01月20日
 */
public class Device {
    // 删除
    public static final Byte DEL_YES = 2;
    // 未删除
    public static final Byte DEL_NO = 1;

    //离线
    public static final Integer OFFLINE = 0;
    //在线
    public static final Integer ONLINE = 1;

    @Id
    private Long id;
    /**
     * dtu id
     */
    private Long dtuId;
    /**
     * 对外id第三方控制时需引用的地址号
     */
    private String externalId;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备编码 串口设备id号,ID号可以是01-16. 最小为1， 最大为16.
     */
    private String code;
    /**
     * 设备通讯电表文件可以存为设备型号
     */
    private String model;

    /**
     * 点位文件存放路径
     */
    private String modelFile;
    /**
     * 在线状态，0->离线，1->在线
     */
    private Integer status;
    /**
     * 显示连线的统计次数
     */
    private Integer connectCount;
    /**
     * 未连接的统计次数
     */
    private Integer disconnectCount;


    /**
     * 最后一次在线时间
     */
    private Date lastOnlineTime;

    /**
     * 最后一次离线时间
     */
    private Date lastOfflineTime;

    /**
     * 设备使用时间
     */
    private Long useTimes;
    /**
     * 删除标记位
     */
    private Byte isDelete;

    /**
     * 定时读取调用设备是否在线的任务名称
     */
    private String taskName;

    /**
     * 创建人
     */
    private Integer createBy;
    /**
     * 更新人
     */
    private Integer updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDtuId() {
        return dtuId;
    }

    public void setDtuId(Long dtuId) {
        this.dtuId = dtuId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getConnectCount() {
        return connectCount;
    }

    public void setConnectCount(Integer connectCount) {
        this.connectCount = connectCount;
    }

    public Integer getDisconnectCount() {
        return disconnectCount;
    }

    public void setDisconnectCount(Integer disconnectCount) {
        this.disconnectCount = disconnectCount;
    }

    public String getModelFile() {
        return modelFile;
    }

    public void setModelFile(String modelFile) {
        this.modelFile = modelFile;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public Date getLastOfflineTime() {
        return lastOfflineTime;
    }

    public void setLastOfflineTime(Date lastOfflineTime) {
        this.lastOfflineTime = lastOfflineTime;
    }

    public Long getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(Long useTimes) {
        this.useTimes = useTimes;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "Device{" + "id=" + id + ", dtuId=" + dtuId + ", externalId='" + externalId + '\''
                       + ", name='" + name + '\'' + ", code='" + code + '\'' + ", model='" + model
                       + '\'' + ", modelFile='" + modelFile + '\'' + ", status=" + status
                       + ", connectCount=" + connectCount + ", disconnectCount=" + disconnectCount
                       + ", lastOnlineTime=" + lastOnlineTime + ", lastOfflineTime="
                       + lastOfflineTime + ", useTimes=" + useTimes + ", isDelete=" + isDelete
                       + ", taskName='" + taskName + '\'' + ", createBy=" + createBy + ", updateBy="
                       + updateBy + '}';
    }
}
