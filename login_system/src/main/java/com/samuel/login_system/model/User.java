package com.samuel.login_system.model;

import jakarta.persistence.*; // Anotações JPA ( Banco de dados)
import lombok.NoArgsConstructor; // Construtor vazio
import lombok.AllArgsConstructor; // Construtor cheio
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
@NoArgsConstructor
@AllArgsConstructor

public class User {

    // 1. Chave Primaria da tabela usuario
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // 2. Dados do usuario
    @Column(nullable = false) // Nao aceita nulo
    private String name;

    @Column(unique = true, nullable = false) // Unico e Obrigatorio
    private String login; // Este será o e-mail

    @Column(nullable = false) // Nao aceita nulo
    private String password;

    // 3. Dados de endereço (Para a API externa)
    private String telephone;

    @Column(length = 8) // Tamanho 8
    private String Cep;

    private String address; // Tudo que vira da API

    // Relacionamento com a classe de dispositivos
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
    private List<Device> devices;

   public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCep() {
        return Cep;
    }

    public void setCep(String cep) {
        this.Cep = cep;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

}
