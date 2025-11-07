package com.senac.academo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarHashSenha {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senha = "123456";
        String hash = encoder.encode(senha);

        System.out.println("Senha: " + senha);
        System.out.println("Hash gerado: " + hash);
        System.out.println("\nTeste de verificação:");
        System.out.println("Hash antigo válido? " + encoder.matches(senha, "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy"));
        System.out.println("Hash novo válido? " + encoder.matches(senha, hash));
    }
}