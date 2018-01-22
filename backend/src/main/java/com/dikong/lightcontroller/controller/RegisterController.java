package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.RegisterService;
import com.dikong.lightcontroller.vo.RegisterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日上午10:17
 * @see </P>
 */
@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping(path = "/register/list")
    public ReturnInfo list(@RequestBody RegisterList registerList){
        return registerService.searchRegister(registerList);
    }
}
