package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;

public interface TxSmsService {

	ReturnInfo<String> sendTxSmsAlarm(String[] phoneNumbers,String[] params);

	ReturnInfo<String> sendTxSmsVoiceAlarm(String[] phoneNumbers,String[] params);
}
