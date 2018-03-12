package com.dikong.lightcontroller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.service.SystemEtcService;

/**
 * @author huangwenjun
 * @version 2018年3月12日 下午11:05:09
 */
@RestController
@RequestMapping("/system/etc")
public class SystemEtcController {

    @Autowired
    private SystemEtcService etcService;

    @GetMapping("/refresh/auth/list")
    public String refreshAuthList() {
        return etcService.refreshAuthList();
    }
}
