package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.MailBean;

public interface MailService {

	ReturnInfo<String> sendMail(MailBean mailBean);

	String templGenertCotent(String[] params);

	String templGennerSubject(String[] params);
}
