package com.dlb.lemon_bank.utils;

import com.dlb.lemon_bank.handler.ErrorType;
import com.dlb.lemon_bank.handler.exception.LemonBankException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String username = null;
        String role = null;
        String jwt;
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (header != null && header.startsWith("Bearer")) {
            jwt = header.substring(7);
            try {
                username = jwtToken.extractUserId(jwt).toString();
                role = jwtToken.extractUserRole(jwt);
            } catch (Exception e) {
                getErrorMessage(response);
            }
        }
        if (role != null) {
            log.info("Role: {}", role);
            authorities.add(new SimpleGrantedAuthority(role));
            log.info("Authorities: {}", authorities);
        }
//        else {
//            authorities = null;
//        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                null,
//                userDetailsService.loadUserByUsername(username).getAuthorities()
                authorities
            );

            SecurityContextHolder.getContext().setAuthentication(token);
            log.info("Token : {}", token);
        }
        filterChain.doFilter(request, response);
    }

    //TODO Исправить эту хуйню
    private void getErrorMessage(HttpServletResponse response)
        throws IOException {
        log.info("Invalid token");
//        response.sendError(HttpStatus.UNAUTHORIZED.value());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(
            Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .build()
                .writeValueAsString("Invalid token")
        );
//        throw new LemonBankException(ErrorType.UNAUTHORIZED);
    }
}
