/*
 * @Date: 2021-07-27 16:34:23
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-19 09:25:37
 */
package com.cloud.note.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenUtil {

    /**
     * @description: 生成token
     * @param {String} mobile
     * @param {Long} expier
     * @param {String} secretKey
     * @return {*}
     */
    public static String createToken(String mobile, Long expier, String secretKey) {
        String token = "";
        try {
            // 过期时间
            Date date = new Date(System.currentTimeMillis() + expier);
            // 私钥加密
            Map<String, Object> header = new HashMap<String, Object>(2);
            header.put("type", "JWT");
            header.put("alg", "HS256");
            token = JWT.create().withHeader(header).withClaim("mobile", mobile).withIssuedAt(new Date())// 设置签发时间
                    .withExpiresAt(date).sign(Algorithm.HMAC256(secretKey));
            return token;
        } catch (Exception e) {
            log.error("Token生成失敗しました。"+e.getMessage());
            return null;
        }
    }

    /**
     * @description: 验证token
     * @param {String} token
     * @param {String} secretKey
     * @param {String} mobile
     * @return {*}
     */
    public static boolean checkToken(String token, String secretKey, String mobile) {
        try {
            // 验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretKey + mobile)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim claimMobile = decodedJWT.getClaim("mobile");
            Date claimTime = decodedJWT.getExpiresAt();
            if (claimTime.before(new Date())) {
                log.info("ユーザー："+mobile+" Token有効期限過ぎました。");
                return false;
            }
            return claimMobile.asString().equals(mobile);
        } catch (Exception e) {
            log.info("ユーザー："+mobile+" Token解析失敗");
            return false;
        }
    }
}
