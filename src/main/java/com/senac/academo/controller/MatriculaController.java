package com.senac.academo.controller;

import com.senac.academo.model.dto.MatriculaDTO;
import com.senac.academo.model.enums.StatusMatricula;
import com.senac.academo.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matriculas")
@CrossOrigin(origins = "*")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;


    @GetMapping
    public ResponseEntity<List<MatriculaDTO>> listarTodas() {
        List<MatriculaDTO> matriculas = matriculaService.findAll();
        return ResponseEntity.ok(matriculas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDTO> buscarPorId(@PathVariable Integer id) {
        MatriculaDTO matricula = matriculaService.findById(id);
        return ResponseEntity.ok(matricula);
    }


    @PostMapping
    public ResponseEntity<MatriculaDTO> criar(@Valid @RequestBody MatriculaDTO matriculaDTO) {
        MatriculaDTO novaMatricula = matriculaService.create(matriculaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMatricula);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MatriculaDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody MatriculaDTO matriculaDTO) {
        MatriculaDTO matriculaAtualizada = matriculaService.update(id, matriculaDTO);
        return ResponseEntity.ok(matriculaAtualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        matriculaService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<MatriculaDTO>> listarPorAluno(@PathVariable Integer alunoId) {
        List<MatriculaDTO> matriculas = matriculaService.findByAluno(alunoId);
        return ResponseEntity.ok(matriculas);
    }


    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<MatriculaDTO>> listarPorDisciplina(@PathVariable Integer disciplinaId) {
        List<MatriculaDTO> matriculas = matriculaService.findByDisciplina(disciplinaId);
        return ResponseEntity.ok(matriculas);
    }


    @GetMapping("/periodo/{periodo}")
    public ResponseEntity<List<MatriculaDTO>> listarPorPeriodo(@PathVariable String periodo) {
        List<MatriculaDTO> matriculas = matriculaService.findByPeriodo(periodo);
        return ResponseEntity.ok(matriculas);
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<MatriculaDTO>> listarPorStatus(@PathVariable String status) {
        StatusMatricula statusMatricula = StatusMatricula.fromValor(status);
        List<MatriculaDTO> matriculas = matriculaService.findByStatus(statusMatricula);
        return ResponseEntity.ok(matriculas);
    }


    @GetMapping("/aluno/{alunoId}/ativas")
    public ResponseEntity<List<MatriculaDTO>> listarAtivasPorAluno(@PathVariable Integer alunoId) {
        List<MatriculaDTO> matriculas = matriculaService.findMatriculasAtivasByAluno(alunoId);
        return ResponseEntity.ok(matriculas);
    }


    @GetMapping("/aluno/{alunoId}/periodo/{periodo}")
    public ResponseEntity<List<MatriculaDTO>> listarPorAlunoEPeriodo(
            @PathVariable Integer alunoId,
            @PathVariable String periodo) {
        List<MatriculaDTO> matriculas = matriculaService.findByAlunoAndPeriodo(alunoId, periodo);
        return ResponseEntity.ok(matriculas);
    }


    @PutMapping("/{id}/trancar")
    public ResponseEntity<MatriculaDTO> trancar(@PathVariable Integer id) {
        MatriculaDTO matricula = matriculaService.trancarMatricula(id);
        return ResponseEntity.ok(matricula);
    }


    @PutMapping("/{id}/reativar")
    public ResponseEntity<MatriculaDTO> reativar(@PathVariable Integer id) {
        MatriculaDTO matricula = matriculaService.reativarMatricula(id);
        return ResponseEntity.ok(matricula);
    }


    @GetMapping("/aluno/{alunoId}/total-ativas")
    public ResponseEntity<Map<String, Object>> contarMatriculasAtivas(@PathVariable Integer alunoId) {
        Long total = matriculaService.contarMatriculasAtivas(alunoId);
        Map<String, Object> response = new HashMap<>();
        response.put("alunoId", alunoId);
        response.put("totalMatriculasAtivas", total);
        return ResponseEntity.ok(response);
    }
}