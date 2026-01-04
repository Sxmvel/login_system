package com.samuel.login_system.service;

import com.samuel.login_system.client.ViaCepClient; // <--- Import do site via CEP
import com.samuel.login_system.dto.UserRegisterDTO;
import com.samuel.login_system.dto.ViaCepResponse; // <--- Import do site via CEP
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

    @Autowired
    private ViaCepClient viaCepClient; // Injeção do Cliente

    public User registerUser(UserRegisterDTO data) {

        // 1. Verifica duplicidade
        Optional<User> userExists = userRepository.findByLogin(data.login());
        if (userExists.isPresent()) {
            throw new RuntimeException("Erro: Email já cadastrado.");
        }

        // 2. Criptografa senha
        String encryptedPassword = passwordEncoder.encode(data.password());

        // 3. Regra de Enriquecimento de Endereço (ViaCEP)
        String fullAddress;
        try {
            // Buscamos os dados na API externa
            ViaCepResponse addressData = viaCepClient.getAddress(data.cep());
            
            // Montamos uma string única para salvar no banco
            fullAddress = addressData.logradouro() + ", " + 
                          addressData.bairro() + " - " + 
                          addressData.localidade() + "/" + 
                          addressData.uf();
                          
        } catch (Exception e) {
            // Se o CEP for inválido ou a API cair, usamos o endereço manual ou deixamos nulo
            // Aqui decidi usar o manual como fallback se o usuário mandou
            fullAddress = data.address() != null ? data.address() : "Endereço não localizado";
        }

        // 4. Monta o Usuário
        User newUser = new User();
        newUser.setName(data.name());
        newUser.setLogin(data.login());
        newUser.setPassword(encryptedPassword);
        newUser.setCep(data.cep());
        newUser.setAddress(fullAddress); // Aqui entra o endereço automático!

        // 5. Salva
        return userRepository.save(newUser);
    }
}