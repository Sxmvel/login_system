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
        
        // 1. Tenta recuperar o token do cabeçalho
        var token = this.recoverToken(request);

        // 2. Se o token existir, valida ele
        if(token != null){
            var login = tokenService.validateToken(token); // Pega o email de dentro do token

            if(login != null && !login.isEmpty()){

                // 3. Busca o usuário no banco
                User user = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User Not Found"));

                // 4. Cria o objeto de autenticação do Spring (Standard)

               
                var authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                
                // 5. Salva no contexto (Agora o Spring sabe quem é você!)
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        // 6. Continua o fluxo (vai para o próximo filtro ou para o Controller)
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para pegar o token "limpo" (sem a palavra Bearer)
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}