package com.senac.academo.service;

import com.senac.academo.exception.ResourceNotFoundException;
import com.senac.academo.mapper.UsuarioMapper;
import com.senac.academo.model.dto.UsuarioDTO;
import com.senac.academo.model.entity.Usuario;
import com.senac.academo.model.enums.TipoUsuario;
import com.senac.academo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;


    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarioMapper.toDTOList(usuarios);
    }


    @Transactional(readOnly = true)
    public UsuarioDTO findById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        return usuarioMapper.toDTO(usuario);
    }


    @Transactional(readOnly = true)
    public UsuarioDTO findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com email: " + email));
        return usuarioMapper.toDTO(usuario);
    }


    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        // Validar se email já existe
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + usuarioDTO.getEmail());
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }


    public UsuarioDTO update(Integer id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        // Validar email se foi alterado
        if (!usuario.getEmail().equals(usuarioDTO.getEmail())) {
            if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                throw new IllegalArgumentException("Email já cadastrado: " + usuarioDTO.getEmail());
            }
        }

        usuarioMapper.updateEntityFromDTO(usuarioDTO, usuario);
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(updatedUsuario);
    }


    public void delete(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }


    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByTipoUsuario(TipoUsuario tipoUsuario) {
        List<Usuario> usuarios = usuarioRepository.findByTipoUsuario(tipoUsuario);
        return usuarioMapper.toDTOList(usuarios);
    }


    @Transactional(readOnly = true)
    public List<UsuarioDTO> findProfessoresAtivos() {
        List<Usuario> professores = usuarioRepository.findProfessoresAtivos();
        return usuarioMapper.toDTOList(professores);
    }


    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAlunosAtivos() {
        List<Usuario> alunos = usuarioRepository.findAlunosAtivos();
        return usuarioMapper.toDTOList(alunos);
    }


    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByNome(String nome) {
        List<Usuario> usuarios = usuarioRepository.findByNomeContaining(nome);
        return usuarioMapper.toDTOList(usuarios);
    }


    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAlunosByDisciplina(Integer disciplinaId) {
        List<Usuario> alunos = usuarioRepository.findAlunosByDisciplinaId(disciplinaId);
        return usuarioMapper.toDTOList(alunos);
    }
}
