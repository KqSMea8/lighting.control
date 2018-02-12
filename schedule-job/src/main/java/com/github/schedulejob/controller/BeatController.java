package com.github.schedulejob.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月29日上午11:06
 * @see
 *      </P>
 */
@RestController
public class BeatController {

    private static final Logger LOG = LoggerFactory.getLogger(BeatController.class);

    @GetMapping(path = "/beat")
    public String beat() {
        LOG.info("beat success ! {}",new Date());
        return "alive";
    }
}
