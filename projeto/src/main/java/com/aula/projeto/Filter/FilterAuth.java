package com.aula.projeto.Filter;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.aula.projeto.User.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;

@Component
public class FilterAuth extends OncePerRequestFilter {
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    String requestURI = request.getRequestURI();

    // Permitir acesso sem autenticação ao H2 Console e ao endpoint de criação de usuários
    if (requestURI.startsWith("/h2-console") || requestURI.startsWith("/user/novoUser")) {
        filterChain.doFilter(request, response);
        return;
    }

        // Corrigir o nome do cabeçalho de "Authorizatiton" para "Authorization"
        var authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Basic ")) {
            response.sendError(401, "Cabeçalho de autorização inválido ou ausente");
            return;
        }

        System.out.println("ALREADY_FILTERED_SUFFIX");
        System.out.println(authorization);

        // Remover a parte "Basic " do valor do cabeçalho
        var authEncode = authorization.substring("Basic".length()).trim();
        System.out.println("ALREADY_FILTERED_SUFFIX");
        System.out.println(authEncode);

        // Decodificar a parte codificada em base64
        byte[] authDecode = Base64.getDecoder().decode(authEncode);
        System.out.println("ALREADY_FILTERED_SUFFIX");
        System.out.println(authDecode);

        // Converter o valor decodificado para uma string
        var authString = new String(authDecode);
        System.out.println(authString);

        // Verificar se o formato está correto (deve conter ":" para separar usuário e senha)
        if (!authString.contains(":")) {
            response.sendError(400, "Formato de credenciais inválido");
            return;
        }

        // Separar o nome de usuário e a senha
        String[] credenciais = authString.split(":");
        
        if (credenciais.length < 2) {
            response.sendError(400, "Credenciais incompletas");
            return;
        }

        String username = credenciais[0];
        String senha = credenciais[1];
        System.out.println(username);
        System.out.println(senha);

        // Validação de usuário
        var user = this.userRepository.findByUsername(username);
        if (user == null) {
            response.sendError(401, "Usuário sem autorização");
        } else {
            var verificaSenha = BCrypt.verifyer().verify(senha.toCharArray(), user.getPassword());
            if (verificaSenha.verified) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401, "Senha inválida");
            }
        }
    }
}