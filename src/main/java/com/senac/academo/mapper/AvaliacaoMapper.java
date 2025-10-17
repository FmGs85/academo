package com.senac.academo.mapper;

import com.senac.academo.model.dto.AvaliacaoDTO;
import com.senac.academo.model.entity.Avaliacao;
import com.senac.academo.model.entity.Disciplina;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvaliacaoMapper {


    public AvaliacaoDTO toDTO(Avaliacao avaliacao) {
        if (avaliacao == null) {
            return null;
        }

        AvaliacaoDTO dto = new AvaliacaoDTO();
        dto.setId(avaliacao.getId());
        dto.setDisciplinaId(avaliacao.getDisciplina().getId());
        dto.setTitulo(avaliacao.getTitulo());
        dto.setDescricao(avaliacao.getDescricao());
        dto.setTipo(avaliacao.getTipo());
        dto.setPeso(avaliacao.getPeso());
        dto.setDataAvaliacao(avaliacao.getDataAvaliacao());
        dto.setDataCriacao(avaliacao.getDataCriacao());

        // Dados adicionais
        dto.setNomeDisciplina(avaliacao.getDisciplina().getNome());
        dto.setCodigoDisciplina(avaliacao.getDisciplina().getCodigo());

        // Determinar status da avaliação
        LocalDate hoje = LocalDate.now();
        if (avaliacao.getDataAvaliacao().isAfter(hoje)) {
            dto.setStatusAvaliacao("Futura");
        } else if (avaliacao.getDataAvaliacao().isEqual(hoje)) {
            dto.setStatusAvaliacao("Em andamento");
        } else {
            dto.setStatusAvaliacao("Realizada");
        }

        return dto;
    }


    public Avaliacao toEntity(AvaliacaoDTO dto, Disciplina disciplina) {
        if (dto == null) {
            return null;
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(dto.getId());
        avaliacao.setDisciplina(disciplina);
        avaliacao.setTitulo(dto.getTitulo());
        avaliacao.setDescricao(dto.getDescricao());
        avaliacao.setTipo(dto.getTipo());
        avaliacao.setPeso(dto.getPeso());
        avaliacao.setDataAvaliacao(dto.getDataAvaliacao());

        return avaliacao;
    }


    public void updateEntityFromDTO(AvaliacaoDTO dto, Avaliacao avaliacao) {
        if (dto.getTitulo() != null) {
            avaliacao.setTitulo(dto.getTitulo());
        }
        if (dto.getDescricao() != null) {
            avaliacao.setDescricao(dto.getDescricao());
        }
        if (dto.getTipo() != null) {
            avaliacao.setTipo(dto.getTipo());
        }
        if (dto.getPeso() != null) {
            avaliacao.setPeso(dto.getPeso());
        }
        if (dto.getDataAvaliacao() != null) {
            avaliacao.setDataAvaliacao(dto.getDataAvaliacao());
        }
    }


    public List<AvaliacaoDTO> toDTOList(List<Avaliacao> avaliacoes) {
        return avaliacoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}