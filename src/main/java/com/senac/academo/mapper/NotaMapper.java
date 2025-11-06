package com.senac.academo.mapper;

import com.senac.academo.model.dto.NotaDTO;
import com.senac.academo.model.entity.Nota;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotaMapper {

    public NotaDTO toDTO(Nota nota) {
        if (nota == null) {
            return null;
        }

        NotaDTO dto = new NotaDTO();
        dto.setId(nota.getId());
        dto.setValor(nota.getValor());
        dto.setDataLancamento(nota.getDataLancamento());
        dto.setObservacao(nota.getObservacao());

        if (nota.getMatricula() != null) {
            dto.setMatriculaId(nota.getMatricula().getId());

            if (nota.getMatricula().getAluno() != null) {
                dto.setAlunoId(nota.getMatricula().getAluno().getId());
                dto.setAlunoNome(nota.getMatricula().getAluno().getNome());
            }

            if (nota.getMatricula().getDisciplina() != null) {
                dto.setDisciplinaId(nota.getMatricula().getDisciplina().getId());
                dto.setDisciplinaNome(nota.getMatricula().getDisciplina().getNome());
            }
        }

        if (nota.getAvaliacao() != null) {
            dto.setAvaliacaoId(nota.getAvaliacao().getId());
            dto.setAvaliacaoTitulo(nota.getAvaliacao().getTitulo());
            dto.setAvaliacaoTipo(nota.getAvaliacao().getTipo() != null ? nota.getAvaliacao().getTipo().name() : null);
        }

        return dto;
    }

    public List<NotaDTO> toDTOList(List<Nota> notas) {
        if (notas == null) {
            return null;
        }
        return notas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Nota toEntity(NotaDTO dto) {
        if (dto == null) {
            return null;
        }

        Nota nota = new Nota();
        nota.setId(dto.getId());
        nota.setValor(dto.getValor());
        nota.setDataLancamento(dto.getDataLancamento());
        nota.setObservacao(dto.getObservacao());

        return nota;
    }

    public List<Nota> toEntityList(List<NotaDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}