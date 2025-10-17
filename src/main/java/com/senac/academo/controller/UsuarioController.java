package com.senac.academo.controller;

import com.senac.academo.model.dto.UsuarioDTO;
import com.senac.academo.model.enums.TipoUsuario;
import com.senac.academo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Integer id) {
        UsuarioDTO usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@PathVariable String email) {
        UsuarioDTO usuario = usuarioService.findByEmail(email);
        return ResponseEntity.ok(usuario);
    }


    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO novoUsuario = usuarioService.create(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioAtualizado = usuarioService.update(id, usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<UsuarioDTO>> buscarPorTipo(@PathVariable String tipo) {
        TipoUsuario tipoUsuario = TipoUsuario.fromValor(tipo);
        List<UsuarioDTO> usuarios = usuarioService.findByTipoUsuario(tipoUsuario);
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/professores")
    public ResponseEntity<List<UsuarioDTO>> listarProfessores() {
        List<UsuarioDTO> professores = usuarioService.findProfessoresAtivos();
        return ResponseEntity.ok(professores);
    }


    @GetMapping("/alunos")
    public ResponseEntity<List<UsuarioDTO>> listarAlunos() {
        List<UsuarioDTO> alunos = usuarioService.findAlunosAtivos();
        return ResponseEntity.ok(alunos);
    }


    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioDTO>> buscarPorNome(@RequestParam String nome) {
        List<UsuarioDTO> usuarios = usuarioService.findByNome(nome);
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/disciplina/{disciplinaId}/alunos")
    public ResponseEntity<List<UsuarioDTO>> listarAlunosDaDisciplina(@PathVariable Integer disciplinaId) {
        List<UsuarioDTO> alunos = usuarioService.findAlunosByDisciplina(disciplinaId);
        return ResponseEntity.ok(alunos);
    }
}