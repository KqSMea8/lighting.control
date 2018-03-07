package com.dikong.lightcontroller.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.common.PageNation;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.LoginRes;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.JedisProxy;
import com.dikong.lightcontroller.utils.SpringContextUtil;

public class LoginHandleInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        String token = request.getHeader(Constant.LOGIN.TOKEN);
        if (StringUtils.isEmpty(token)) {
            response(response, CodeEnum.NO_LOGIN, "");
            return false;
        }
        JedisPool jedisPool = (JedisPool) SpringContextUtil.getBean(JedisPool.class);
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        String userInfo = jedis.get(token);
        if (StringUtils.isEmpty(userInfo)) {
            response(response, CodeEnum.NO_LOGIN, "");
            return false;
        }
        LoginRes currentUserInfo = JSON.parseObject(userInfo, LoginRes.class);
        AuthCurrentUser.set(currentUserInfo);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {
        // AuthCurrentUser.remove();
    }

    private void response(HttpServletResponse response, CodeEnum codeEnum, Object data)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Content-Type", "application/json");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(ReturnInfo.create(codeEnum.getCode(), codeEnum.getMsg(), data,
                new PageNation())));
        out.flush();
    }
}
