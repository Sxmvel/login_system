package com.samuel.login_system.service;

import com.samuel.login_system.dto.UserRegisterDTO;
import com.samuel.login_system.model.User;
import com.samuel.login_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service 
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserRegisterDTO data) {

        // 1. Verificar se usuário já existe
        Optional<User> userExists = userRepository.findByLogin(data.login());
        
        if (userExists.isPresent()) {
            throw new RuntimeException("Erro: Email já cadastrado no sistema.");
        }

        // 2. Criptografar a senha
        String encryptedPassword = passwordEncoder.encode(data.password());

        // 3. Conversão (DTO -> Entity)
        User newUser = new User();
        newUser.setName(data.name());
        newUser.setLogin(data.login());
        newUser.setPassword(encryptedPassword); 
        newUser.setCep(data.cep());
        newUser.setAddress(data.address());

        // 4. Persistência
        return userRepository.save(newUser);
    }
}
