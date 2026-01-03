package com.samuel.login_system.dto;

public record UserRegisterDTO(
    String name,
    String login, // email
    String password,
    String cep,
    String address // Opcional, caso o front já mande
) {}
