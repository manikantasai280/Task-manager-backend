package com.MKSProjects.SpringSecurity.security;

import com.MKSProjects.SpringSecurity.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 🔹 1. Get Authorization header
        final String authHeader = request.getHeader("Authorization");

        // 🔹 2. If header missing or invalid → skip
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 🔹 3. Extract JWT token
        final String jwt = authHeader.substring(7);

        // 🔹 4. Extract username from token
        final String username = jwtService.extractUsername(jwt);

        // 🔹 5. If username exists and not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 🔹 6. Load user from DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 🔹 7. Validate token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 🔹 8. Create authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 🔹 9. Attach request details (IP, session, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 🔹 10. Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 🔹 11. Continue filter chain
        filterChain.doFilter(request, response);
    }
}