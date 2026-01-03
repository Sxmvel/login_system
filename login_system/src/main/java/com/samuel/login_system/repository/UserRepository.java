package com.samuel.login_system.repository;

import com.samuel.login_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    // Magic Method: O Spring lê "findByLogin" e cria o SQL:
    // SELECT * FROM tb_users WHERE login = ?
    Optional<User> findByLogin(String login);

    // Optional evita o NullPointerException se a busca vier vazia

}
