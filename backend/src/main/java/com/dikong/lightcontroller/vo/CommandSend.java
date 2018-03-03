package com.dikong.lightcontroller.vo;

import java.util.Arrays;
import java.util.List;

import com.dikong.lightcontroller.dto.CmdSendDto;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午9:01
 * @see
 *      </P>
 */
public class CommandSend {

    // 时序表id
    private Long timingId;

    // 执行的变量id,可以是一个群组下面的所有变量
    /**
     * 存储每个变量对应要执行的值
     */
    private List<CmdSendDto> varIdS;

    private String taskName;


    private Integer projId;

    public Long getTimingId() {
        return timingId;
    }

    public void setTimingId(Long timingId) {
        this.timingId = timingId;
    }

    public List<CmdSendDto> getVarIdS() {
        return varIdS;
    }

    public void setVarIdS(List<CmdSendDto> varIdS) {
        this.varIdS = varIdS;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    @Override
    public String toString() {
        return "CommandSend{" + "timingId=" + timingId + ", varIdS="
                + (varIdS != null ? Arrays.asList(varIdS) : varIdS) + ", taskName='" + taskName
                + '\'' + ", projId=" + projId + '}';
    }
}
