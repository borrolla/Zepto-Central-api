package com.central.zepto.central_api.Security;

import com.central.zepto.central_api.Util.DatabaseAPIUtil;
import com.central.zepto.central_api.models.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    String jwtSecret;

    Long expirationMilis = 100000L;

    @Autowired
    DatabaseAPIUtil dbApi;

    //credentials/// email .com:12345
    public String generateJwtToken(String credentials){

       String token = Jwts.builder()
                        .setSubject(credentials)
                        .setExpiration(new Date(System.currentTimeMillis() + expirationMilis))
               .setIssuedAt(new Date())
               .signWith(SignatureAlgorithm.HS256, jwtSecret)
               .compact();

      return token;
    }

    public String decodeJwtToken(String token){

       String credentials =  Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

            return credentials;
    }
    public boolean isValidToken(String token){

        String credentials = this.decodeJwtToken(token);
        String userEmail = credentials.split(":")[0];
        String userPassword = credentials.split(":")[1];
        //Database->User
       AppUser user = dbApi.getUserByEmail(userEmail);
       if(user != null && user.getPassword().equals(userPassword))
       {
           return true;
       }
       return false;
    }
}
