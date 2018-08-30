package com.dikong.lightcontroller.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.TxSmsService;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.SmsVoicePromptSender;
import com.github.qcloudsms.TtsVoiceSender;
import com.github.qcloudsms.TtsVoiceSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

@Service
public class TxSmsServiceImpl implements TxSmsService {

    private Integer appid;
    private String appkey;
    private Integer alarmTemplateId;
    private Integer voiceAlarmTemplateId;
    private String smsSign;
    private SmsSingleSender ssender;
    private SmsMultiSender msender;
    private SmsVoicePromptSender vpsender;
    private TtsVoiceSender ttsVoiceSender;

    public TxSmsServiceImpl() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        this.appid = Integer.parseInt(properties.getProperty("tenx.sms.appid"));
        this.appkey = properties.getProperty("tenx.sms.appkey");
        this.alarmTemplateId =
                Integer.parseInt(properties.getProperty("tenx.sms.alarm.templateId"));
        this.voiceAlarmTemplateId =
                Integer.parseInt(properties.getProperty("tenx.smsvoice.alarm.templateId"));
        this.smsSign = properties.getProperty("tenx.sms.alarm.smsSign");
        this.ssender = new SmsSingleSender(appid, appkey);
        this.msender = new SmsMultiSender(appid, appkey);
        this.vpsender = new SmsVoicePromptSender(appid, appkey);
        this.ttsVoiceSender = new TtsVoiceSender(appid, appkey);

        String smsBug = properties.getProperty("tenx.sms.debug");
        if (StringUtils.isEmpty(smsBug)) {
            smsBug = "false";
        }
        Boolean debug = Boolean.parseBoolean(smsBug);
        if (debug) {
            // http://hc.apache.org/httpcomponents-client-ga/logging.html
            System.setProperty("log4j.rootLogger", "INFO, stdout");
            System.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
            System.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
            System.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%5p [%c] %m%n");
            System.setProperty("log4j.logger.org.apache.http", "DEBUG");
            System.setProperty("log4j.logger.org.apache.http.wire", "ERRO");
        }
    }

    /**
     * 批量发送短信
     * 
     * @param phoneNumbers
     * @param params
     * @return
     */
    @Override
    public ReturnInfo<String> sendTxSmsAlarm(String[] phoneNumbers, String[] params) {
        if (phoneNumbers.length == 0) {
            return ReturnInfo.create("");
        }
        // 单条发送
        if (phoneNumbers.length == 1) {
            SmsSingleSenderResult result = null;
            try {
                result = ssender.sendWithParam("86", phoneNumbers[0], this.alarmTemplateId, params,
                        this.smsSign, "", "");
            } catch (HTTPException e) {
                e.printStackTrace();
                return ReturnInfo.create("");
            } catch (IOException e) {
                e.printStackTrace();
                return ReturnInfo.create("");
            }
            if (result != null) {
                return ReturnInfo.create(JSON.toJSONString(result));
            }
            return ReturnInfo.create("");
        }
        // 批量发送
        SmsMultiSenderResult result = null;
        try {
            result = msender.sendWithParam("86", phoneNumbers, this.alarmTemplateId, params,
                    smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
        } catch (HTTPException e) {
            e.printStackTrace();
            return ReturnInfo.create("");
        } catch (IOException e) {
            e.printStackTrace();
            return ReturnInfo.create("");
        }
        if (result != null) {
            return ReturnInfo.create(JSON.toJSONString(result));
        }
        return ReturnInfo.create("");
    }

    /**
     * 批量发送语音短信
     * 
     * @param phoneNumbers
     * @param params
     * @return
     */
    @Override
    public ReturnInfo<String> sendTxSmsVoiceAlarm(String[] phoneNumbers, String[] params) {
        if (phoneNumbers.length == 0) {
            return ReturnInfo.create("");
        }
        List<TtsVoiceSenderResult> results = new ArrayList<>();
        for (String phoneNumber : phoneNumbers) {
            TtsVoiceSenderResult send = null;
            try {
                send = ttsVoiceSender.send("86", phoneNumber, this.voiceAlarmTemplateId, params, 2,
                        "");
            } catch (HTTPException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (send != null) {
                results.add(send);
            }
        }
        return ReturnInfo.create(JSON.toJSONString(results));
    }
}
