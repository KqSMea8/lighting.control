package com.dikong.lightcontroller.dto;

import com.dikong.lightcontroller.entity.EquipmentMonitor;

/**
 * @author huangwenjun
 * @version 2018年3月9日 下午9:37:56
 */
public class OneMonitorInfo extends EquipmentMonitor {
    private String sourceName;
    private String varName;

    public OneMonitorInfo() {
        super();
        this.sourceName = "";
        this.varName = "";
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
}
