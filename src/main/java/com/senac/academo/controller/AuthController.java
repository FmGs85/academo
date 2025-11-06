package com.senac.academo.controller;

import com.senac.academo.config.JwtUtil;
import com.senac.academo.model.dto.LoginRequestDTO;
import com.senac.academo.model.dto.LoginResponseDTO;
import com.senac.academo.model.dto.UsuarioDTO;
import com.senac.academo.model.entity.Usuario;
import com.senac.academo.repository.UsuarioRepository;
import com.senac.academo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            // Autenticar usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()
                    )
            );

            // Buscar usuário
            Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

            // Gerar token
            String token = jwtUtil.generateToken(
                    usuario.getEmail(),
                    usuario.getId(),
                    usuario.getTipoUsuario().name()
            );

            // Converter para DTO
            UsuarioDTO usuarioDTO = usuarioService.convertToDTO(usuario);

            // Criar resposta
            LoginResponseDTO response = new LoginResponseDTO(token, usuarioDTO);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha inválidos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao realizar login: " + e.getMessage());
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Verificar se o email já existe
            if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email já cadastrado");
            }

            // Criptografar senha
            usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

            // Criar usuário
            UsuarioDTO novoUsuario = usuarioService.create(usuarioDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao realizar registro: " + e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtUtil.extractUsername(token);

                Usuario usuario = usuarioRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

                if (jwtUtil.validateToken(token, email)) {
                    UsuarioDTO usuarioDTO = usuarioService.convertToDTO(usuario);
                    return ResponseEntity.ok(usuarioDTO);
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }
}