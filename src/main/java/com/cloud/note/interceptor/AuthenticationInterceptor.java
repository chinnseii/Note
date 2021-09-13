/*
 * @Date: 2021-07-27 16:57:06
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-08 14:40:14
 * @FilePath: \note\src\main\java\com\cloud\note\interceptor\AuthenticationInterceptor.java
 */
package com.cloud.note.interceptor;

import com.cloud.note.annotation.PassToken;
import com.cloud.note.annotation.TokenCheck;
import com.cloud.note.entity.Constant;
import com.cloud.note.utils.TokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private Constant constant;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object object) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        // 检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        // 检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(TokenCheck.class)) {
            TokenCheck userLoginToken = method.getAnnotation(TokenCheck.class);
            if (userLoginToken.required()) {
                String header = httpServletRequest.getHeader("header");// 从 http 请求头中取出
                // 获取 token 中的 user id
                JSONObject jsonObject = new JSONObject(header);
                String token = jsonObject.getString("token");
                String userMobile = jsonObject.getString("userMobile");
                JSONObject res = new JSONObject();
                // 执行认证
                if (token.equals("")) {
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.setContentType("application/json; charset=utf-8");
                    httpServletResponse.sendRedirect("login");
                    res.put("errorCode", 401);                   
                    log.info(constant.getTOKEN_NULL_ERRORMSG()+" IP:"+httpServletRequest.getRemoteAddr());
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write(res.toString());
                    out.flush();
                    out.close();              
                    return false;
                }
                if (!TokenUtil.checkToken(token, constant.getSecretkey(), userMobile)) {
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.setContentType("application/json; charset=utf-8");
                    res.put("errorCode", 403);  
                    log.info("ユーザー:"+userMobile+" Token有効期限過ぎました IP:"+httpServletRequest.getRemoteAddr());
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write(res.toString());
                    out.flush();
                    out.close();
                    return false;
                }
            }
        }
        return true;
    }
}