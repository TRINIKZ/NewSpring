package com.aula.projeto.User;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/user")
public class UserController {

    

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/novoUser")
    public ResponseEntity<Object> createUser(@RequestBody UserModel userModel, HttpServletRequest request) {
        var existente = this.userRepository.findByNome(userModel.getNome());
        if (existente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cadastro existente");
        }

        if (userModel.getSenha() == null || userModel.getSenha().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha não pode ser nula ou vazia");
        }

        var senhaHash = BCrypt.withDefaults()
            .hashToString(12, userModel.getSenha().toCharArray());

        userModel.setSenha(senhaHash);
        var criado = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/cadUsers")
    public ResponseEntity<Object> listarUsuarios() {
        List<UserModel> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum usuário encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/atualiza")
    public ResponseEntity<Object> atualizaUser(@RequestBody UserModel userModel) {
        if (userModel.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID é obrigatório para atualização");
        }

        var existente = userRepository.findById(userModel.getId());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        if (userModel.getSenha() != null && !userModel.getSenha().trim().isEmpty()) {
            var senhaHash = BCrypt.withDefaults().hashToString(12, userModel.getSenha().toCharArray());
            userModel.setSenha(senhaHash);
        } else {
            userModel.setSenha(existente.get().getSenha());
        }

        var atualizado = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

    @Transactional
    @DeleteMapping("/deletauser/{id}")
    public ResponseEntity<Object> deletaUser(@PathVariable UUID id) {
        var existente = userRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }
}
