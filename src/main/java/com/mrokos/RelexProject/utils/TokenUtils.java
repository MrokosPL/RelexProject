package com.mrokos.RelexProject.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TokenUtils {
    @Value("${relexproject.secret}")
    private String secret;
    @Value("${tokenlifetime}")
    private Duration tokenLifetime;

    public String tokenGeneration(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        List<String> roleList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("permissions", roleList);
        Date date = new Date();
        Date tokenExpirationDate = new Date(date.getTime() + tokenLifetime.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(date)
                .setExpiration(tokenExpirationDate)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
    private Claims getClaims (String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getJwtMail(String token){
        return getClaims(token).getSubject();
    }
    public List<String> getJwtRole(String token){
        return getClaims(token).get("permissions", List.class);
    }
}
