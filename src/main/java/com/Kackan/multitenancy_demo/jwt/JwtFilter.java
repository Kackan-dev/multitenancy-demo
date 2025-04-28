package com.Kackan.multitenancy_demo.jwt;

import com.Kackan.multitenancy_demo.tenant.TenantContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            Claims claims = parseJwt(token);
            String username = claims.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (validateJwtToken(userDetails, username)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                TenantContext.setCurrentTenant(username);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            };
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clearTenantContext();
        }
    }

    private Claims parseJwt(String jwt){
        return Jwts.parser()
                .verifyWith(getMySecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    private boolean validateJwtToken(UserDetails userDetails, String usernameFromToken) {
        if (userDetails == null || !StringUtils.hasLength(usernameFromToken)) {
            return false;
        }
        return userDetails.getUsername().equals(usernameFromToken);
    }

    private SecretKey getMySecretKey() {
        return Keys.hmacShaKeyFor("very_hard_and_very_secret_kackan_secret_key".getBytes()); //TODO only to simplicity - secret key should not be storing in code
    }
}
