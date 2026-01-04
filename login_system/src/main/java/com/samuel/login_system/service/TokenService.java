package com.samuel.login_system.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.samuel.login_system.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // 1. GERA O TOKEN (Cria o crachá)
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            
            String token = JWT.create()
                    .withIssuer("login-auth-api") // Quem emitiu (nossa API)
                    .withSubject(user.getLogin()) // O "dono" do token (email)
                    .withExpiresAt(genExpirationDate()) // Validade (2 horas)
                    .sign(algorithm); // Assina digitalmente
            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    // 2. VALIDA O TOKEN (Lê o crachá)
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token) // Se for falso ou expirado, joga erro
                    .getSubject(); // Retorna o email do usuário
        } catch (JWTVerificationException exception){
            return ""; // Se der erro, retorna vazio
        }
    }

    // Define que o token expira em 2 horas
    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}