package com.dikong.lightcontroller.vo;

import java.util.Map;

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
    private Map<Long, Integer> varIdS;

    private String taskName;

    public Long getTimingId() {
        return timingId;
    }

    public void setTimingId(Long timingId) {
        this.timingId = timingId;
    }

    public Map<Long, Integer> getVarIdS() {
        return varIdS;
    }

    public void setVarIdS(Map<Long, Integer> varIdS) {
        this.varIdS = varIdS;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
