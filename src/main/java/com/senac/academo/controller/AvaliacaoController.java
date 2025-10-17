package com.senac.academo.controller;

import com.senac.academo.model.dto.AvaliacaoDTO;
import com.senac.academo.model.enums.TipoAvaliacao;
import com.senac.academo.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;


    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> listarTodas() {
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.findAll();
        return ResponseEntity.ok(avaliacoes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> buscarPorId(@PathVariable Integer id) {
        AvaliacaoDTO avaliacao = avaliacaoService.findById(id);
        return ResponseEntity.ok(avaliacao);
    }


    @PostMapping
    public ResponseEntity<AvaliacaoDTO> criar(@Valid @RequestBody AvaliacaoDTO avaliacaoDTO) {
        AvaliacaoDTO novaAvaliacao = avaliacaoService.create(avaliacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AvaliacaoDTO avaliacaoDTO) {
        AvaliacaoDTO avaliacaoAtualizada = avaliacaoService.update(id, avaliacaoDTO);
        return ResponseEntity.ok(avaliacaoAtualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        avaliacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<AvaliacaoDTO>> listarPorDisciplina(@PathVariable Integer disciplinaId) {
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.findByDisciplina(disciplinaId);
        return ResponseEntity.ok(avaliacoes);
    }


    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<AvaliacaoDTO>> listarPorTipo(@PathVariable String tipo) {
        TipoAvaliacao tipoAvaliacao = TipoAvaliacao.fromValor(tipo);
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.findByTipo(tipoAvaliacao);
        return ResponseEntity.ok(avaliacoes);
    }


    @GetMapping("/disciplina/{disciplinaId}/tipo/{tipo}")
    public ResponseEntity<List<AvaliacaoDTO>> listarPorDisciplinaETipo(
            @PathVariable Integer disciplinaId,
            @PathVariable String tipo) {
        TipoAvaliacao tipoAvaliacao = TipoAvaliacao.fromValor(tipo);
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.findByDisciplinaAndTipo(disciplinaId, tipoAvaliacao);
        return ResponseEntity.ok(avaliacoes);
    }


    @GetMapping("/data/{data}")
    public ResponseEntity<List<AvaliacaoDTO>> listarPorData(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data) {
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.findByData(data);
        return ResponseEntity.ok(avaliacoes);
    }


    @GetMapping("/periodo")
    public ResponseEntity<List<AvaliacaoDTO>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.findByDataBetween(dataInicio, dataFim);
        return ResponseEntity.ok(avaliacoes);
    }


    @GetMapping("/disciplina/{disciplinaId}/futuras")
    public ResponseEntity<List<AvaliacaoDTO>> listarFuturas(@PathVariable Integer disciplinaId) {
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.findAvaliacoesFuturas(disciplinaId);
        return ResponseEntity.ok(avaliacoes);
    }


    @GetMapping("/disciplina/{disciplinaId}/realizadas")
    public ResponseEntity<List<AvaliacaoDTO>> listarRealizadas(@PathVariable Integer disciplinaId) {
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.findAvaliacoesRealizadas(disciplinaId);
        return ResponseEntity.ok(avaliacoes);
    }


    @GetMapping("/disciplina/{disciplinaId}/ordenadas")
    public ResponseEntity<List<AvaliacaoDTO>> listarOrdenadas(@PathVariable Integer disciplinaId) {
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.findByDisciplinaOrderByData(disciplinaId);
        return ResponseEntity.ok(avaliacoes);
    }


    @GetMapping("/disciplina/{disciplinaId}/total")
    public ResponseEntity<Map<String, Object>> contarAvaliacoes(@PathVariable Integer disciplinaId) {
        Long total = avaliacaoService.contarAvaliacoes(disciplinaId);
        Map<String, Object> response = new HashMap<>();
        response.put("disciplinaId", disciplinaId);
        response.put("totalAvaliacoes", total);
        return ResponseEntity.ok(response);
    }
}