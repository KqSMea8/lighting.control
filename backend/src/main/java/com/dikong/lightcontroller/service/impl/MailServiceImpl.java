package com.dikong.lightcontroller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.MailBean;
import com.dikong.lightcontroller.service.MailService;
import org.springframework.util.StringUtils;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private Environment environment;

    @Override
    public ReturnInfo<String> sendMail(MailBean mailBean) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBean.getTo());
        if (StringUtils.isEmpty(mailBean.getSubject())) {
            message.setSubject(environment.getProperty("mail.subject"));
        } else {
            message.setSubject(mailBean.getSubject());
        }
        message.setText(mailBean.getContent());
        message.setCc(mailBean.getCc());
        message.setFrom(environment.getProperty("spring.mail.username"));
        emailSender.send(message);
        return ReturnInfo.create("success");
    }

    /**
     * 生成内容
     * @param params
     * @return
     */
    @Override
    public String templGenertCotent(String[] params) {
        String content = environment.getProperty("mail.cotent.templet");
        for (int i = 0; i < params.length; i++) {
            content = StringUtils.replace(content,"{"+(i+1)+"}",params[i]);
        }
        return null;
    }

    /**
     * 生产主题名字
     * @param params
     * @return
     */
    @Override
    public String templGennerSubject(String[] params) {
        String subject = environment.getProperty("mail.subject.templet");
        for (int i = 0; i < params.length; i++) {
            subject = StringUtils.replace(subject,"{"+(i+1)+"}",params[i]);
        }
        return subject;
    }
}
