package com.senac.academo.controller;

import com.senac.academo.mapper.MatriculaMapper;
import com.senac.academo.model.dto.MatriculaDTO;
import com.senac.academo.model.entity.Matricula;
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

    @Autowired
    private MatriculaMapper matriculaMapper;

    @GetMapping
    public ResponseEntity<List<MatriculaDTO>> listarTodas() {
        List<Matricula> matriculas = matriculaService.listarTodas();
        List<MatriculaDTO> dtos = matriculaMapper.toDTOList(matriculas);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDTO> buscarPorId(@PathVariable Integer id) {
        Matricula matricula = matriculaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));
        MatriculaDTO dto = matriculaMapper.toDTO(matricula);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<MatriculaDTO> criar(@Valid @RequestBody MatriculaDTO matriculaDTO) {
        Matricula matricula = matriculaMapper.toEntity(matriculaDTO);
        Matricula novaMatricula = matriculaService.criarMatricula(matricula);
        MatriculaDTO dto = matriculaMapper.toDTO(novaMatricula);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody MatriculaDTO matriculaDTO) {
        Matricula matricula = matriculaMapper.toEntity(matriculaDTO);
        Matricula matriculaAtualizada = matriculaService.atualizarMatricula(id, matricula);
        MatriculaDTO dto = matriculaMapper.toDTO(matriculaAtualizada);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        matriculaService.deletarMatricula(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<MatriculaDTO>> listarPorAluno(@PathVariable Integer alunoId) {
        List<Matricula> matriculas = matriculaService.buscarPorAluno(alunoId);
        List<MatriculaDTO> dtos = matriculaMapper.toDTOList(matriculas);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<MatriculaDTO>> listarPorDisciplina(@PathVariable Integer disciplinaId) {
        List<Matricula> matriculas = matriculaService.buscarPorDisciplina(disciplinaId);
        List<MatriculaDTO> dtos = matriculaMapper.toDTOList(matriculas);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MatriculaDTO>> listarPorStatus(@PathVariable String status) {
        StatusMatricula statusMatricula = StatusMatricula.valueOf(status.toUpperCase());
        List<Matricula> matriculas = matriculaService.buscarPorStatus(statusMatricula);
        List<MatriculaDTO> dtos = matriculaMapper.toDTOList(matriculas);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/aluno/{alunoId}/ativas")
    public ResponseEntity<List<MatriculaDTO>> listarAtivasPorAluno(@PathVariable Integer alunoId) {
        List<Matricula> matriculas = matriculaService.buscarPorAluno(alunoId);
        // Filtrar apenas as ativas
        List<Matricula> ativas = matriculas.stream()
                .filter(m -> m.getStatus() == StatusMatricula.ATIVA)
                .toList();
        List<MatriculaDTO> dtos = matriculaMapper.toDTOList(ativas);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/aluno/{alunoId}/total-ativas")
    public ResponseEntity<Map<String, Object>> contarMatriculasAtivas(@PathVariable Integer alunoId) {
        List<Matricula> matriculas = matriculaService.buscarPorAluno(alunoId);
        long total = matriculas.stream()
                .filter(m -> m.getStatus() == StatusMatricula.ATIVA)
                .count();

        Map<String, Object> response = new HashMap<>();
        response.put("alunoId", alunoId);
        response.put("totalMatriculasAtivas", total);
        return ResponseEntity.ok(response);
    }
}