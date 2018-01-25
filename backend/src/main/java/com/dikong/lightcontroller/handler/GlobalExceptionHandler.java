package com.dikong.lightcontroller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月23日下午3:48
 * @see
 *      </P>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public ReturnInfo exceptionHandler(Exception exception) {
        exception.printStackTrace();
        return ReturnInfo.create(CodeEnum.SERVER_ERROR);
    }
}
