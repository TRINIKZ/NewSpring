package com.aula.projeto.User;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity (name = "tb_user")
public class UserModel {

    @Id
    @GeneratedValue (generator = "UUID")
    private UUID id;

    private String nome;
    private String username;
    private String senha;
    private String telefone;
    private String email;
    private String endereco;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereço(String endereço) {
        this.endereco = endereço;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senhaHash) {
        this.senha = senhaHash;
    }
    public String getPassword() {
        return this.senha;
    }
    
}