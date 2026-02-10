package com.liu.studentmanagement.utils;

import com.liu.studentmanagement.common.enums.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;




public class JwtUtils {

    // 签名密钥（生产环境应该更复杂且放在配置文件里）
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // 有效期：24小时
    private static final long EXPIRE = 24 * 60 * 60 * 1000;

    /**
     * 生成 Token
     */
    public static String createToken(Integer userId, String username, RoleEnum role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRE);

        return Jwts.builder()
                .setSubject("USER_INFO")
                .claim("userId", userId)      // 存入用户ID
                .claim("username", username)  // 存入用户名
                .claim("role", role.getCode())
                .setIssuedAt(now)             // 签发时间
                .setExpiration(expiryDate)    // 过期时间
                .signWith(KEY)                // 签名
                .compact();
    }

    /**
     * 解析并验证 Token
     * 如果成功，返回内容（Claims）；如果失败（过期或伪造），抛出异常
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}