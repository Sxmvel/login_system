package com.samuel.login_system.controller;

import com.samuel.login_system.dto.UserRegisterDTO;
import com.samuel.login_system.model.User;
import com.samuel.login_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth") // Agrupa os endpoints de autenticação
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterDTO data, UriComponentsBuilder uriBuilder) {
        // 1. Chama o serviço para salvar
        User newUser = userService.registerUser(data);

        // 2. Cria a URL de acesso ao novo recurso (Boa prática REST)
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(newUser.getId()).toUri();

        // 3. Retorna 201 Created com o corpo do usuário criado
        return ResponseEntity.created(uri).body(newUser);
    }
}