package com.senac.academo.mapper;

import com.senac.academo.model.dto.UsuarioDTO;
import com.senac.academo.model.entity.Usuario;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {


    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        dto.setDataCadastro(usuario.getDataCadastro());
        dto.setAtivo(usuario.getAtivo());


        return dto;
    }


    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTipoUsuario(dto.getTipoUsuario());
        usuario.setAtivo(dto.getAtivo());


        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenhaHash(hashSenha(dto.getSenha()));
        }

        return usuario;
    }


    public void updateEntityFromDTO(UsuarioDTO dto, Usuario usuario) {
        if (dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getTipoUsuario() != null) {
            usuario.setTipoUsuario(dto.getTipoUsuario());
        }
        if (dto.getAtivo() != null) {
            usuario.setAtivo(dto.getAtivo());
        }
        // Atualizar senha se fornecida
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenhaHash(hashSenha(dto.getSenha()));
        }
    }


    public List<UsuarioDTO> toDTOList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    private String hashSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }
}
