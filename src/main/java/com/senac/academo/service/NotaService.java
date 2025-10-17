package com.senac.academo.service;

import com.senac.academo.exception.ResourceNotFoundException;
import com.senac.academo.model.dto.NotaDTO;
import com.senac.academo.model.entity.Avaliacao;
import com.senac.academo.model.entity.Matricula;
import com.senac.academo.model.entity.Nota;
import com.senac.academo.repository.AvaliacaoRepository;
import com.senac.academo.repository.MatriculaRepository;
import com.senac.academo.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;


    @Transactional(readOnly = true)
    public List<NotaDTO> findAll() {
        return notaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public NotaDTO findById(Integer id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota não encontrada com ID: " + id));
        return convertToDTO(nota);
    }


    public NotaDTO lancarNota(NotaDTO notaDTO) {
        // Verificar se matrícula existe
        Matricula matricula = matriculaRepository.findById(notaDTO.getMatriculaId())
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula não encontrada"));

        // Verificar se avaliação existe
        Avaliacao avaliacao = avaliacaoRepository.findById(notaDTO.getAvaliacaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        // Verificar se já existe nota para esta matrícula e avaliação
        if (notaRepository.existsByMatriculaIdAndAvaliacaoId(
                notaDTO.getMatriculaId(), notaDTO.getAvaliacaoId())) {
            throw new IllegalArgumentException("Já existe nota lançada para esta avaliação");
        }

        Nota nota = new Nota();
        nota.setMatricula(matricula);
        nota.setAvaliacao(avaliacao);
        nota.setNota(notaDTO.getNota());
        nota.setObservacoes(notaDTO.getObservacoes());

        Nota savedNota = notaRepository.save(nota);
        return convertToDTO(savedNota);
    }


    public NotaDTO atualizarNota(Integer id, NotaDTO notaDTO) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota não encontrada com ID: " + id));

        nota.setNota(notaDTO.getNota());
        if (notaDTO.getObservacoes() != null) {
            nota.setObservacoes(notaDTO.getObservacoes());
        }

        Nota updatedNota = notaRepository.save(nota);
        return convertToDTO(updatedNota);
    }


    public void delete(Integer id) {
        if (!notaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nota não encontrada com ID: " + id);
        }
        notaRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public List<NotaDTO> findByMatriculaId(Integer matriculaId) {
        return notaRepository.findByMatriculaId(matriculaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<NotaDTO> findByAlunoIdAndDisciplinaId(Integer alunoId, Integer disciplinaId) {
        return notaRepository.findByAlunoIdAndDisciplinaId(alunoId, disciplinaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public BigDecimal calcularMediaPonderada(Integer matriculaId) {
        BigDecimal media = notaRepository.calcularMediaPonderada(matriculaId);
        return media != null ? media.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }


    private NotaDTO convertToDTO(Nota nota) {
        NotaDTO dto = new NotaDTO();
        dto.setId(nota.getId());
        dto.setMatriculaId(nota.getMatricula().getId());
        dto.setAvaliacaoId(nota.getAvaliacao().getId());
        dto.setNota(nota.getNota());
        dto.setObservacoes(nota.getObservacoes());
        dto.setDataLancamento(nota.getDataLancamento());

        // Dados adicionais
        dto.setNomeAluno(nota.getMatricula().getAluno().getNome());
        dto.setNomeDisciplina(nota.getMatricula().getDisciplina().getNome());
        dto.setTituloAvaliacao(nota.getAvaliacao().getTitulo());
        dto.setTipoAvaliacao(nota.getAvaliacao().getTipo().getDescricao());
        dto.setPesoAvaliacao(nota.getAvaliacao().getPeso());

        return dto;
    }
}