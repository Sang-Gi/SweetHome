package com.ssafy.sweethome.user.model.service;

import com.ssafy.sweethome.exception.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    public static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    private static final String SALT = "ssafySecret";
    private static final int ACCESS_TOKEN_EXPIRE_MINUTES = 1;
    private static final int REFRESH_TOKEN_EXPIRE_MINUTES = 2;

    @Override
    public <T> String createAccessToken(String key, T data) {
        return create(key, data, "access-token", 1000 * 60 * 10 * ACCESS_TOKEN_EXPIRE_MINUTES);
//        return create(key, data, "access-token", 1000 * 10 * ACCESS_TOKEN_EXPIRE_MINUTES);
    }

    @Override
    public <T> String createRefreshToken(String key, T data) {
        return create(key, data, "refresh-token", 1000 * 60 * 60 * 24 * 7 * REFRESH_TOKEN_EXPIRE_MINUTES);
//        return create(key, data, "refresh-token", 1000 * 30 * ACCESS_TOKEN_EXPIRE_MINUTES);
    }

    @Override
    public <T> String create(String key, T data, String subject, long expire) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .setSubject(subject)
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
    }

    private byte[] generateKey() {
        return SALT.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public boolean checkToken(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
            logger.debug("claims: {}", claims);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Map<String, Object> get(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String jwt = request.getHeader("access-token");
        Jws<Claims> claims;
        try {
            claims = Jwts.parser().setSigningKey(SALT.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(jwt);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UnAuthorizedException();
        }
        Map<String, Object> value = claims.getBody();
        logger.info("value : {}", value);
        return value;
    }

    @Override
    public String getUserId() {
        return (String) this.get("user").get("userid");
    }
}
