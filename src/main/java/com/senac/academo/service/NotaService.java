package com.senac.academo.service;

import com.senac.academo.exception.ResourceNotFoundException;
import com.senac.academo.mapper.NotaMapper;
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

import java.util.List;

@Service
@Transactional
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private NotaMapper notaMapper;

    @Transactional(readOnly = true)
    public List<NotaDTO> findAll() {
        List<Nota> notas = notaRepository.findAll();
        return notaMapper.toDTOList(notas);
    }

    @Transactional(readOnly = true)
    public NotaDTO findById(Integer id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota não encontrada com ID: " + id));
        return notaMapper.toDTO(nota);
    }

    public NotaDTO create(NotaDTO notaDTO) {
        // Validar matrícula
        Matricula matricula = matriculaRepository.findById(notaDTO.getMatriculaId())
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula não encontrada"));

        // Validar avaliação
        Avaliacao avaliacao = avaliacaoRepository.findById(notaDTO.getAvaliacaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        // Validar se já existe nota para esta matrícula e avaliação
        if (notaRepository.existsByMatriculaIdAndAvaliacaoId(
                notaDTO.getMatriculaId(), notaDTO.getAvaliacaoId())) {
            throw new IllegalArgumentException(
                    "Já existe uma nota lançada para esta avaliação nesta matrícula");
        }

        // Validar valor da nota
        if (notaDTO.getValor() < 0 || notaDTO.getValor() > 10) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
        }

        Nota nota = new Nota();
        nota.setMatricula(matricula);
        nota.setAvaliacao(avaliacao);
        nota.setValor(notaDTO.getValor());
        nota.setObservacao(notaDTO.getObservacao());

        Nota savedNota = notaRepository.save(nota);

        // Atualizar média final da matrícula
        atualizarMediaMatricula(matricula.getId());

        return notaMapper.toDTO(savedNota);
    }

    public NotaDTO update(Integer id, NotaDTO notaDTO) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota não encontrada com ID: " + id));

        // Validar valor da nota
        if (notaDTO.getValor() < 0 || notaDTO.getValor() > 10) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
        }

        nota.setValor(notaDTO.getValor());
        nota.setObservacao(notaDTO.getObservacao());

        Nota updatedNota = notaRepository.save(nota);

        // Atualizar média final da matrícula
        atualizarMediaMatricula(nota.getMatricula().getId());

        return notaMapper.toDTO(updatedNota);
    }

    public void delete(Integer id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota não encontrada com ID: " + id));

        Integer matriculaId = nota.getMatricula().getId();
        notaRepository.deleteById(id);

        // Atualizar média final da matrícula
        atualizarMediaMatricula(matriculaId);
    }

    @Transactional(readOnly = true)
    public List<NotaDTO> findByMatricula(Integer matriculaId) {
        List<Nota> notas = notaRepository.findByMatriculaId(matriculaId);
        return notaMapper.toDTOList(notas);
    }

    @Transactional(readOnly = true)
    public List<NotaDTO> findByAvaliacao(Integer avaliacaoId) {
        List<Nota> notas = notaRepository.findByAvaliacaoId(avaliacaoId);
        return notaMapper.toDTOList(notas);
    }

    @Transactional(readOnly = true)
    public List<NotaDTO> findByAluno(Integer alunoId) {
        List<Nota> notas = notaRepository.findByMatriculaAlunoId(alunoId);
        return notaMapper.toDTOList(notas);
    }

    @Transactional(readOnly = true)
    public List<NotaDTO> findByDisciplina(Integer disciplinaId) {
        List<Nota> notas = notaRepository.findByMatriculaDisciplinaId(disciplinaId);
        return notaMapper.toDTOList(notas);
    }

    @Transactional(readOnly = true)
    public Double calcularMediaMatricula(Integer matriculaId) {
        List<Nota> notas = notaRepository.findByMatriculaId(matriculaId);

        if (notas.isEmpty()) {
            return 0.0;
        }

        double somaNotas = 0.0;
        double somaPesos = 0.0;

        for (Nota nota : notas) {
            if (nota.getAvaliacao() != null) {
                double peso = nota.getAvaliacao().getPeso() != null ? nota.getAvaliacao().getPeso() : 1.0;
                somaNotas += nota.getValor() * peso;
                somaPesos += peso;
            }
        }

        return somaPesos > 0 ? somaNotas / somaPesos : 0.0;
    }

    private void atualizarMediaMatricula(Integer matriculaId) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula não encontrada"));

        Double media = calcularMediaMatricula(matriculaId);
        matricula.setMediaFinal(media);

        // Atualizar situação baseado na média
        if (media >= 7.0) {
            matricula.setSituacao(com.senac.academo.model.enums.SituacaoMatricula.APROVADO);
        } else if (media >= 4.0) {
            matricula.setSituacao(com.senac.academo.model.enums.SituacaoMatricula.CURSANDO);
        } else {
            matricula.setSituacao(com.senac.academo.model.enums.SituacaoMatricula.REPROVADO);
        }

        matriculaRepository.save(matricula);
    }
    @Transactional(readOnly = true)
    public List<NotaDTO> findByMatriculaId(Integer matriculaId) {
        return findByMatricula(matriculaId);
    }

    @Transactional(readOnly = true)
    public List<NotaDTO> findByAlunoIdAndDisciplinaId(Integer alunoId, Integer disciplinaId) {
        List<Nota> notas = notaRepository.findByMatriculaAlunoIdAndMatriculaDisciplinaId(alunoId, disciplinaId);
        return notaMapper.toDTOList(notas);
    }

    @Transactional(readOnly = true)
    public java.math.BigDecimal calcularMediaPonderada(Integer matriculaId) {
        Double media = calcularMediaMatricula(matriculaId);
        return java.math.BigDecimal.valueOf(media);
    }
}