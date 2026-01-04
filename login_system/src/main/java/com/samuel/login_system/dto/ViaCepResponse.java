package com.samuel.login_system.dto;

public record ViaCepResponse(
    String logradouro,
    String bairro,
    String localidade, // A cidade
    String uf
) {}