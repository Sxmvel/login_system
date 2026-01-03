package com.samuel.login_system.model;

import jakarta.persistence.*; // Anotações JPA ( Banco de dados)
import lombok.Data; // Getters / Setters Automaticos
import lombok.NoArgsConstructor; // Construtor vazio
import lombok.AllArgsConstructor; // Construtor cheio

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {

    // 1. Chave Primaria da tabela usuario
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // 2. Dados do usuario
    @Column(nullable = false) // Nao aceita nulo
    private String nome;

    @Column(unique = true, nullable = false) // Unico e Obrigatorio
    private String login; // Este será o e-mail

    @Column(nullable = false) // Nao aceita nulo
    private String password;

    // 3. Dados de endereço (Para a API externa)
    private String telephone;

    @Column(length = 8) // Tamanho 8
    private String CEP;

    private String address; // Tudo que vira da API

    // Relacionamento com a classe de dispositivos
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
    private List<Device> devices;

}
