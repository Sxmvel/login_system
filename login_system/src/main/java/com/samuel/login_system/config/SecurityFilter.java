package com.samuel.login_system.config;

import com.samuel.login_system.model.User;
import com.samuel.login_system.repository.UserRepository;
import com.samuel.login_system.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
       
        var token = this.recoverToken(request);
       

        if(token != null){
            var login = tokenService.validateToken(token);
           

            if(login != null && !login.isEmpty()){
                User user = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User Not Found"));

                var authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
            }
        }
        
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para pegar o token "limpo" 
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}