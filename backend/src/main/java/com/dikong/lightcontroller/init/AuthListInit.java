package com.dikong.lightcontroller.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.dikong.lightcontroller.service.SystemEtcService;

/**
 * @author huangwenjun
 * @Date 2018年3月12日
 */
@Component
@Order(10)
public class AuthListInit implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(AuthListInit.class);

    @Autowired
    private SystemEtcService etcService;

    @Override
    public void run(String... args) throws Exception {
        etcService.refreshAuthList();
    }
}
