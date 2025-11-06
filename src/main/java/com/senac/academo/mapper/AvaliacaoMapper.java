package com.senac.academo.mapper;

import com.senac.academo.model.dto.AvaliacaoDTO;
import com.senac.academo.model.entity.Avaliacao;
import com.senac.academo.model.entity.Disciplina;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

        // Converter Double para BigDecimal
        if (avaliacao.getPeso() != null) {
            dto.setPeso(BigDecimal.valueOf(avaliacao.getPeso()));
        }

        dto.setDataAvaliacao(avaliacao.getData());
        // REMOVIDO: dto.setDataCriacao - não existe na entidade

        // Dados adicionais
        dto.setNomeDisciplina(avaliacao.getDisciplina().getNome());
        dto.setCodigoDisciplina(avaliacao.getDisciplina().getCodigo());

        // Determinar status da avaliação
        LocalDate hoje = LocalDate.now();
        if (avaliacao.getData().isAfter(hoje)) {
            dto.setStatusAvaliacao("Futura");
        } else if (avaliacao.getData().isEqual(hoje)) {
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

        // Converter BigDecimal para Double
        if (dto.getPeso() != null) {
            avaliacao.setPeso(dto.getPeso().doubleValue());
        }

        avaliacao.setData(dto.getDataAvaliacao());

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
            // Converter BigDecimal para Double
            avaliacao.setPeso(dto.getPeso().doubleValue());
        }
        if (dto.getDataAvaliacao() != null) {
            avaliacao.setData(dto.getDataAvaliacao());
        }
    }

    public List<AvaliacaoDTO> toDTOList(List<Avaliacao> avaliacoes) {
        return avaliacoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}