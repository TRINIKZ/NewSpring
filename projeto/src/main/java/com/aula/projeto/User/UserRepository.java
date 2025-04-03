package com.aula.projeto.User;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByNome(String nome);
    UserModel findByUsername(String username);
}