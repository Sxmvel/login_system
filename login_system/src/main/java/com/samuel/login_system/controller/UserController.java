package com.samuel.login_system.controller;

import com.samuel.login_system.dto.AuthenticationDTO;
import com.samuel.login_system.dto.LoginResponseDTO;
import com.samuel.login_system.dto.UserRegisterDTO;
import com.samuel.login_system.model.User;
import com.samuel.login_system.repository.UserRepository;
import com.samuel.login_system.service.TokenService;
import com.samuel.login_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    

    @Autowired
    private UserRepository userRepository; 
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        
        // --- LOGS DE DEBUG ---
        System.out.println("=== TENTATIVA DE LOGIN ===");
        System.out.println("Email recebido: " + data.getLogin()); // Mudou de .login() para .getLogin()
        System.out.println("Senha recebida: " + data.getPassword()); // Mudou de .password() para .getPassword()

        // 1. Busca o usuário (Usando getLogin)
        User user = userRepository.findByLogin(data.getLogin()).orElseThrow(() -> new RuntimeException("User not found"));
        
        System.out.println("Usuário achado no banco (ID): " + user.getId());
        System.out.println("Hash da senha no banco: " + user.getPassword());

        // 2. Compara senha (Usando getPassword)
        boolean matches = passwordEncoder.matches(data.getPassword(), user.getPassword());
        System.out.println("A senha confere? " + matches);
 
        if(matches) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponseDTO(token));
        }
    
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterDTO data, UriComponentsBuilder uriBuilder) {
        User newUser = userService.registerUser(data);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).body(newUser);
    }
}