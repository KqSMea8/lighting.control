package com.dikong.lightcontroller.service.impl;

import com.dikong.lightcontroller.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月27日下午5:13
 * @see </P>
 */
@Service
public class HelloServiceImpl implements HelloService{
    public void hello() {
        System.out.println("=====================================");
        System.out.println("hello Java Language");
        System.out.println("=====================================");
    }
}
