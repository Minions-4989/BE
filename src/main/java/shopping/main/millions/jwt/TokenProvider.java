package shopping.main.millions.jwt;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import shopping.main.millions.jwt.dto.LoginTokenSaveDto;
import shopping.main.millions.jwt.dto.Token;
import shopping.main.millions.jwt.service.CustomUserDetailsService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;


@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private final CustomUserDetailsService customUserDetailsService;

    @Value("${app.auth.tokenExpiry}")
    private Long accessTokenExpiry;
    @Value("${app.auth.refreshTokenExpiry}")
    private Long refreshTokenExpiry;
    @Value("${app.auth.tokenSecret}")
    private String secretKey;
    private String ROLES = "roles";

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token createToken(Long userPk, LoginTokenSaveDto roles){
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
        claims.put(ROLES,roles);

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiry))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpiry))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .issuedAt(now)
                .accessTokenExpireDate(new Date(now.getTime() + accessTokenExpiry))
                .build();
    }

    public Authentication getAuthentication(String token){

        Claims claims = parseClaims(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }


    public Claims parseClaims(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    public String getUserPk(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }



    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validationToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

}
