package com.dikong.lightcontroller.utils;

import org.slf4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月25日下午2:57
 * @see
 *      </P>
 */
public class ValidateLogUtil {

    public static ReturnInfo paramError(BindingResult bindingResult, Logger log) {
        String s = "";
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(
                    "[" + fieldError.getField() + ":" + fieldError.getDefaultMessage() + "]");
        }
        s = builder.toString();
        log.info(s);
        return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR, s);
    }
}
