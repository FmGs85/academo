package com.senac.academo.controller;

import com.senac.academo.model.dto.NotaDTO;
import com.senac.academo.service.NotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/notas")
@CrossOrigin(origins = "*")
public class NotaController {

    @Autowired
    private NotaService notaService;


    @GetMapping
    public ResponseEntity<List<NotaDTO>> listarTodas() {
        List<NotaDTO> notas = notaService.findAll();
        return ResponseEntity.ok(notas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<NotaDTO> buscarPorId(@PathVariable Integer id) {
        NotaDTO nota = notaService.findById(id);
        return ResponseEntity.ok(nota);
    }


    @PostMapping
    public ResponseEntity<NotaDTO> lancarNota(@Valid @RequestBody NotaDTO notaDTO) {
        NotaDTO novaNota = notaService.create(notaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaNota);
    }


    @PutMapping("/{id}")
    public ResponseEntity<NotaDTO> atualizarNota(
            @PathVariable Integer id,
            @Valid @RequestBody NotaDTO notaDTO) {
        NotaDTO notaAtualizada = notaService.update(id, notaDTO);
        return ResponseEntity.ok(notaAtualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        notaService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<NotaDTO>> buscarPorMatricula(@PathVariable Integer matriculaId) {
        List<NotaDTO> notas = notaService.findByMatriculaId(matriculaId);
        return ResponseEntity.ok(notas);
    }


    @GetMapping("/aluno/{alunoId}/disciplina/{disciplinaId}")
    public ResponseEntity<List<NotaDTO>> buscarPorAlunoEDisciplina(
            @PathVariable Integer alunoId,
            @PathVariable Integer disciplinaId) {
        List<NotaDTO> notas = notaService.findByAlunoIdAndDisciplinaId(alunoId, disciplinaId);
        return ResponseEntity.ok(notas);
    }


    @GetMapping("/matricula/{matriculaId}/media")
    public ResponseEntity<MediaResponse> calcularMedia(@PathVariable Integer matriculaId) {
        BigDecimal media = notaService.calcularMediaPonderada(matriculaId);
        MediaResponse response = new MediaResponse(media);
        return ResponseEntity.ok(response);
    }


    public static class MediaResponse {
        private BigDecimal mediaPonderada;
        private String situacao;

        public MediaResponse(BigDecimal mediaPonderada) {
            this.mediaPonderada = mediaPonderada;

            // Determinar situação
            if (mediaPonderada.compareTo(new BigDecimal("7.00")) >= 0) {
                this.situacao = "Aprovado";
            } else if (mediaPonderada.compareTo(new BigDecimal("4.00")) >= 0) {
                this.situacao = "Recuperação";
            } else {
                this.situacao = "Reprovado";
            }
        }



        public BigDecimal getMediaPonderada() { return mediaPonderada; }
        public void setMediaPonderada(BigDecimal mediaPonderada) { this.mediaPonderada = mediaPonderada; }
        public String getSituacao() { return situacao; }
        public void setSituacao(String situacao) { this.situacao = situacao; }
    }
}