package com.dikong.lightcontroller.entity;

import java.util.Optional;

import junit.framework.TestCase;

public class DtuTest extends TestCase {


    public void testDtu() {
        Dtu dtu = new Dtu();
//        dtu.setDeviceName("dtu");
//        dtu.setDevice("device");
        Optional<Dtu> dtu1 = Optional.of(dtu);
        System.out.println(dtu1.isPresent());
    }

    /**
     * DTU设备
     */
    private String device;
    /**
     * 设备名称
     */
    private String deviceName;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
