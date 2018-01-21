package com.dikong.lightcontroller.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午1:46
 * @see </P>
 */
@RestController
public class BeatController {

    @RequestMapping(path = "/beat")
    public String beat(){
        return "alive";
    }
}
