package com.devop.aashish.java.myapplication.configuration.security.jwt;

import com.devop.aashish.java.myapplication.application.exception.UnAuthenticatedException;
import com.devop.aashish.java.myapplication.configuration.security.ApplicationUserAuthService;
import com.devop.aashish.java.myapplication.configuration.security.vo.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.expiration.user}")
    private int expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    @Autowired
    private ApplicationUserAuthService applicationUserAuthService;

    public String generateUserToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);
        String token;
        token = Jwts.builder()
                .setSubject((userPrincipal.getUserId()))
                .claim("userDetails", userPrincipal.toString())
                .claim("authorities", userPrincipal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();


        return token;
    }


    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }


    boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnAuthenticatedException("Expired or invalid JWT token");
        }
    }

    Authentication getAuthentication(String userId) {
        UserDetails userDetails = applicationUserAuthService.loadUserById(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
