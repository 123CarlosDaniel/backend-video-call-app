package com.cdcm.backend.security.filters;

import com.cdcm.backend.entity.UserEntity;
import com.cdcm.backend.repository.UserRepository;
import com.cdcm.backend.security.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final List<RequestMatcher> EXCLUDED_ENDPOINTS = List.of(
            new AntPathRequestMatcher("/api/v1/auth/login"),
            new AntPathRequestMatcher("/api/v1/auth/register")
    );
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private boolean matchesExcludedEndpoints(HttpServletRequest request) {
        return EXCLUDED_ENDPOINTS.stream().anyMatch(matcher -> matcher.matches(request));
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        if (matchesExcludedEndpoints(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = authHeader.substring(7);
        String username;
        try {
            username = jwtUtils.extractUsername(jwtToken);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
            return;
        }
        UserEntity user = null;
        try {
            user = userRepository.findUserEntityByUsername(username).orElseThrow();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
            return;
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
