package com.dikong.lightcontroller.utils.cmd;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.dikong.lightcontroller.entity.Register;

/**
 * @author huangwenjun
 * @Datetime 2018年1月26日
 */
public class CmdMsgUtils {

    public static final Integer MAXVALUE = 65535;

    public static String assembleSendCmd(String dtuAddr, ReadWriteEnum operationType,
            String varType, int addressInfo, SwitchEnum switchEnum) {
        return assembleSendCmd(dtuAddr, operationType, varType, addressInfo, switchEnum.getValue());
    }

    public static String assembleSendCmd(String dtuAddr, ReadWriteEnum operationType,
            String varType, int addressInfo, int value) {
        if (value > MAXVALUE || dtuAddr.length() > 2) {
            return null;
        }
        return assembleSendCmd(dtuAddr, operationType, varType, addressInfo,
                Integer.toHexString(value));
    }


    public static String assembleSendCmd(String dtuAddr, ReadWriteEnum operationType,
            String varType, int addressInfo, String value) {
        if (Register.DEFAULT_CONNCTION_ADDR.equals(addressInfo)) {
            return null;
        }
        String functionCode = "";
        int[] addressinfos = new int[1];
        addressinfos[0] = addressInfo;
        if (operationType == ReadWriteEnum.READ) {
            functionCode = choiceReadFuncCode(addressinfos);
        } else if (operationType == ReadWriteEnum.WRITE) {
            functionCode = choiceWriteFuncCode(addressinfos);
        } else {
            return null;
        }
        addressInfo = addressinfos[0];
        if (addressInfo > MAXVALUE) {
            return null;
        }

        String address = SimpleStringUtils.repair4Char(Integer.toHexString(addressInfo - 1));
        value = SimpleStringUtils.repair4Char(value);
        dtuAddr = SimpleStringUtils.repairChar(2, dtuAddr);
        String cmdHalf = dtuAddr + functionCode + address + value;
        String crc = crcCheck(cmdHalf);

        return cmdHalf + crc.substring(2, 4) + crc.substring(0, 2);
    }

    /**
     * 顺序读取返回值即可
     * 
     * @param recieveCmd
     */
    public static List<String> analysisSwitchCmd(String recieveCmd, int length) {
        List<String> switchInfo = new ArrayList<String>();
        if (StringUtils.isEmpty(recieveCmd)) {
            return switchInfo;
        }
        int byteNum = Integer.parseInt(recieveCmd.substring(4, 6), 16);
        int[] currentIndex = new int[1];
        for (int i = 0; i < byteNum * 2; i += 2) {
            String binHighStr =
                    Integer.toBinaryString(Integer.parseInt(recieveCmd.substring(i + 6, i + 7), 16));
            String binLowStr =
                    Integer.toBinaryString(Integer.parseInt(recieveCmd.substring(i + 7, i + 8), 16));
            String bin4CharLow = SimpleStringUtils.repair4Char(binLowStr);
            String bin4CharHigh = SimpleStringUtils.repair4Char(binHighStr);
            switchChange(bin4CharLow, currentIndex, length, switchInfo);
            switchChange(bin4CharHigh, currentIndex, length, switchInfo);
        }
        return switchInfo;
    }

    private static void switchChange(String bin4Char, int[] currentIndex, int length,
            List<String> switchInfo) {
        for (int j = 3; j >= 0; j--) {
            if (currentIndex[0] >= length) {
                break;
            }
            currentIndex[0]++;
            if (bin4Char.charAt(j) == '1') {
                switchInfo.add("1");
            } else {
                switchInfo.add("0");
            }
        }
    }

    public static List<String> analysisAnalogCmd(String recieveCmd) {
        List<String> values = new ArrayList<>();
        if (StringUtils.isEmpty(recieveCmd)) {
            return values;
        }
        int byteNum = Integer.parseInt(recieveCmd.substring(4, 6), 16);
        for (int i = 0; i < byteNum / 2; i++) {
            int value = Integer.parseInt(recieveCmd.substring(i * 4 + 6, i * 4 + 10), 16);
            values.add(String.valueOf(value));
        }
        return values;
    }

    private static String choiceReadFuncCode(int[] addressinfo) {
        if (addressinfo[0] > 0 && addressinfo[0] <= 9999) {
            return "01";
        } else if (addressinfo[0] > 10000 && addressinfo[0] <= 19999) {
            addressinfo[0] -= 10000;
            return "02";
        } else if (addressinfo[0] > 40000 && addressinfo[0] <= 49999) {
            addressinfo[0] -= 40000;
            return "03";
        } else if (addressinfo[0] > 30000 && addressinfo[0] <= 39999) {
            addressinfo[0] -= 30000;
            return "04";
        } else {
            return null;
        }
    }

    private static String choiceWriteFuncCode(int[] addressinfo) {
        if (addressinfo[0] >= 0 && addressinfo[0] <= 9999) {
            return "05";
        } else if (addressinfo[0] > 40000 && addressinfo[0] <= 49999) {
            addressinfo[0] -= 40000;
            return "06";
        } else {
            return null;
        }
    }

    public static String crcCheck(String msg) {
        int crc = 0XFFFF;
        for (int i = 0; i < msg.length() / 2; i++) {
            crc = crc ^ Integer.parseInt(msg.substring(i * 2, i * 2 + 2), 16);
            for (int j = 0; j < 8; j++) {
                int result = crc & 1;
                if (result != 0) {
                    crc >>= 1;
                    crc ^= 0XA001;
                } else
                    crc >>= 1;
            }
        }
        return SimpleStringUtils.repair4Char(Integer.toHexString(crc));
    }

    public static String strTo16(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
}
