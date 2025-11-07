package com.senac.academo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AcademoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademoApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("🚀 ACADEMO API INICIADA COM SUCESSO!");
        System.out.println("📱 Sistema Acadêmico Mobile - Backend");
        System.out.println("🌐 URL: http://localhost:8081/swagger-ui/index.html");
        System.out.println("========================================\n");
    }
}
