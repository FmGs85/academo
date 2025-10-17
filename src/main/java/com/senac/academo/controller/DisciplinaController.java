package com.senac.academo.controller;

import com.senac.academo.model.dto.DisciplinaDTO;
import com.senac.academo.service.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/disciplinas")
@CrossOrigin(origins = "*")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;


    @GetMapping
    public ResponseEntity<List<DisciplinaDTO>> listarTodas() {
        List<DisciplinaDTO> disciplinas = disciplinaService.findAll();
        return ResponseEntity.ok(disciplinas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaDTO> buscarPorId(@PathVariable Integer id) {
        DisciplinaDTO disciplina = disciplinaService.findById(id);
        return ResponseEntity.ok(disciplina);
    }


    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<DisciplinaDTO> buscarPorCodigo(@PathVariable String codigo) {
        DisciplinaDTO disciplina = disciplinaService.findByCodigo(codigo);
        return ResponseEntity.ok(disciplina);
    }


    @PostMapping
    public ResponseEntity<DisciplinaDTO> criar(@Valid @RequestBody DisciplinaDTO disciplinaDTO) {
        DisciplinaDTO novaDisciplina = disciplinaService.create(disciplinaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDisciplina);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DisciplinaDTO disciplinaDTO) {
        DisciplinaDTO disciplinaAtualizada = disciplinaService.update(id, disciplinaDTO);
        return ResponseEntity.ok(disciplinaAtualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        disciplinaService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/ativas")
    public ResponseEntity<List<DisciplinaDTO>> listarAtivas() {
        List<DisciplinaDTO> disciplinas = disciplinaService.findAtivas();
        return ResponseEntity.ok(disciplinas);
    }


    @GetMapping("/buscar")
    public ResponseEntity<List<DisciplinaDTO>> buscarPorNome(@RequestParam String nome) {
        List<DisciplinaDTO> disciplinas = disciplinaService.findByNome(nome);
        return ResponseEntity.ok(disciplinas);
    }


    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<DisciplinaDTO>> listarPorProfessor(@PathVariable Integer professorId) {
        List<DisciplinaDTO> disciplinas = disciplinaService.findByProfessor(professorId);
        return ResponseEntity.ok(disciplinas);
    }


    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<DisciplinaDTO>> listarPorAluno(@PathVariable Integer alunoId) {
        List<DisciplinaDTO> disciplinas = disciplinaService.findByAluno(alunoId);
        return ResponseEntity.ok(disciplinas);
    }


    @GetMapping("/periodo/{periodo}")
    public ResponseEntity<List<DisciplinaDTO>> listarPorPeriodo(@PathVariable String periodo) {
        List<DisciplinaDTO> disciplinas = disciplinaService.findByPeriodo(periodo);
        return ResponseEntity.ok(disciplinas);
    }


    @GetMapping("/{id}/total-alunos")
    public ResponseEntity<Map<String, Object>> contarAlunos(@PathVariable Integer id) {
        Long totalAlunos = disciplinaService.contarAlunosMatriculados(id);
        Map<String, Object> response = new HashMap<>();
        response.put("disciplinaId", id);
        response.put("totalAlunos", totalAlunos);
        return ResponseEntity.ok(response);
    }
}