package com.dlb.lemon_bank.utils;

import com.dlb.lemon_bank.domain.entity.UserEntity;
import com.dlb.lemon_bank.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.expiration-access}")
    private long jwtAccessExpiration;
    @Value("${jwt.secret-key}")
    private String jwtSecretKey;
    private final UserRepository userRepository;

    public String generateToken(Integer userId) {
        return buildToken(Map.of("role", getRole(userId)), userId);
    }

    public Integer extractUserId(String token) {
        log.info("Extract user id from token {}", token);
        return Integer.valueOf(extractClaim(token, Claims::getSubject));
    }

    public String extractUserRole(String token) {
        log.info("Extract user role from token {}", token);
        return extractClaimRole(token);
    }

    private String buildToken(Map<String, Object> extraClaims, Integer userId) {
        log.info("Build new token");
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userId.toString())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + this.jwtAccessExpiration * 1000))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String extractClaimRole(String token) {
        final Claims claims = extractAllClaims(token);
        log.info("Claims role:{}", claims.get("role"));
        return claims.get("role").toString();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String getRole(Integer userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getUserRole();
        }
        else {
            return "";
        }
    }

}
