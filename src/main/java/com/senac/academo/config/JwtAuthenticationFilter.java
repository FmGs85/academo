package com.senac.academo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // DEBUG: Log da requisição
        System.out.println("========================================");
        System.out.println("🔍 JWT FILTER DEBUG");
        System.out.println("📍 Path: " + request.getRequestURI());
        System.out.println("🔑 Auth Header: " + (authHeader != null ? "Presente (Bearer ...)" : "Ausente"));

        // Extrair token do header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            System.out.println("✅ Token extraído (primeiros 20 chars): " + jwt.substring(0, Math.min(20, jwt.length())) + "...");

            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println("👤 Username extraído: " + username);
            } catch (Exception e) {
                System.err.println("❌ ERRO ao extrair username: " + e.getMessage());
                logger.error("Erro ao extrair username do token: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ Header Authorization ausente ou inválido");
        }

        // Validar token e autenticar usuário
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("🔄 Carregando UserDetails para: " + username);

            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                System.out.println("✅ UserDetails carregado");
                System.out.println("👥 Authorities: " + userDetails.getAuthorities());

                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    System.out.println("✅ Token válido! Autenticando usuário...");

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("✅ Usuário autenticado com sucesso!");
                } else {
                    System.err.println("❌ Token INVÁLIDO!");
                }
            } catch (Exception e) {
                System.err.println("❌ ERRO ao carregar UserDetails: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (username == null) {
            System.out.println("⚠️ Username não extraído do token");
        } else {
            System.out.println("ℹ️ Usuário já autenticado no contexto");
        }

        System.out.println("========================================");
        filterChain.doFilter(request, response);
    }
}