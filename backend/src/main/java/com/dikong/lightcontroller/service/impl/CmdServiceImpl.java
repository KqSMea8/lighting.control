package com.dikong.lightcontroller.service.impl;

import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.utils.cmd.CmdMsgUtils;
import com.dikong.lightcontroller.utils.cmd.ReadWriteEnum;
import com.dikong.lightcontroller.utils.cmd.SwitchEnum;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:28:30
 */
@Service
public class CmdServiceImpl implements CmdService {

    @Override
    public void readSwitch(String deviceCode, long devId, long varId, SwitchEnum switchEnum) {
        // 根据dtuId、devId查询 在DTU中得顺序，根据顺序判断地址，最好来一个顺序，就是串口设备地址
        int equipmentOrder = 0;
        // 根据 long varId,查询变量信息
        String varType = "BI";
        int addressInfo = 1;// 起始地址
        int value;// 查询地址数量
        String sendMsg = CmdMsgUtils.assembleSendCmd(equipmentOrder, ReadWriteEnum.READ, varType,
                addressInfo, switchEnum);
    }

    @Override
    public void writeSwitch() {
        // 根据dtuId、devId查询 在DTU中得顺序，根据顺序判断地址
        int equipmentOrder = 0;
        String varType = "BI";
        int addressInfo = 1;// 变量地址
        int value;// 变量值

    }

    @Override
    public void readAnalog() {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeAnalog() {
        // TODO Auto-generated method stub

    }

}
