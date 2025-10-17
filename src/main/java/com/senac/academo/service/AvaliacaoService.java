package com.senac.academo.service;

import com.senac.academo.exception.ResourceNotFoundException;
import com.senac.academo.mapper.AvaliacaoMapper;
import com.senac.academo.model.dto.AvaliacaoDTO;
import com.senac.academo.model.entity.Avaliacao;
import com.senac.academo.model.entity.Disciplina;
import com.senac.academo.model.enums.TipoAvaliacao;
import com.senac.academo.repository.AvaliacaoRepository;
import com.senac.academo.repository.DisciplinaRepository;
import com.senac.academo.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private AvaliacaoMapper avaliacaoMapper;


    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> findAll() {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll();
        return avaliacaoMapper.toDTOList(avaliacoes);
    }


    @Transactional(readOnly = true)
    public AvaliacaoDTO findById(Integer id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada com ID: " + id));

        AvaliacaoDTO dto = avaliacaoMapper.toDTO(avaliacao);

        // Adicionar total de notas lançadas
        Long totalNotas = notaRepository.countByAvaliacaoId(id);
        dto.setTotalNotasLancadas(totalNotas);

        return dto;
    }


    public AvaliacaoDTO create(AvaliacaoDTO avaliacaoDTO) {
        // Validar se disciplina existe
        Disciplina disciplina = disciplinaRepository.findById(avaliacaoDTO.getDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada"));

        Avaliacao avaliacao = avaliacaoMapper.toEntity(avaliacaoDTO, disciplina);
        Avaliacao savedAvaliacao = avaliacaoRepository.save(avaliacao);
        return avaliacaoMapper.toDTO(savedAvaliacao);
    }


    public AvaliacaoDTO update(Integer id, AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada com ID: " + id));

        avaliacaoMapper.updateEntityFromDTO(avaliacaoDTO, avaliacao);
        Avaliacao updatedAvaliacao = avaliacaoRepository.save(avaliacao);
        return avaliacaoMapper.toDTO(updatedAvaliacao);
    }


    public void delete(Integer id) {
        if (!avaliacaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Avaliação não encontrada com ID: " + id);
        }
        avaliacaoRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> findByDisciplina(Integer disciplinaId) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByDisciplinaId(disciplinaId);
        return avaliacaoMapper.toDTOList(avaliacoes);
    }


    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> findByTipo(TipoAvaliacao tipo) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByTipo(tipo);
        return avaliacaoMapper.toDTOList(avaliacoes);
    }


    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> findByDisciplinaAndTipo(Integer disciplinaId, TipoAvaliacao tipo) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByDisciplinaIdAndTipo(disciplinaId, tipo);
        return avaliacaoMapper.toDTOList(avaliacoes);
    }


    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> findByData(LocalDate data) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByDataAvaliacao(data);
        return avaliacaoMapper.toDTOList(avaliacoes);
    }


    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> findByDataBetween(LocalDate dataInicio, LocalDate dataFim) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByDataAvaliacaoBetween(dataInicio, dataFim);
        return avaliacaoMapper.toDTOList(avaliacoes);
    }


    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> findAvaliacoesFuturas(Integer disciplinaId) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAvaliacoesFuturasByDisciplinaId(disciplinaId);
        return avaliacaoMapper.toDTOList(avaliacoes);
    }


    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> findAvaliacoesRealizadas(Integer disciplinaId) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAvaliacoesRealizadasByDisciplinaId(disciplinaId);
        return avaliacaoMapper.toDTOList(avaliacoes);
    }


    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> findByDisciplinaOrderByData(Integer disciplinaId) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByDisciplinaIdOrderByDataAvaliacao(disciplinaId);
        return avaliacaoMapper.toDTOList(avaliacoes);
    }


    @Transactional(readOnly = true)
    public Long contarAvaliacoes(Integer disciplinaId) {
        return avaliacaoRepository.countByDisciplinaId(disciplinaId);
    }
}