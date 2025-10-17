package com.senac.academo.mapper;

import com.senac.academo.model.dto.NotaDTO;
import com.senac.academo.model.entity.Avaliacao;
import com.senac.academo.model.entity.Matricula;
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
        dto.setMatriculaId(nota.getMatricula().getId());
        dto.setAvaliacaoId(nota.getAvaliacao().getId());
        dto.setNota(nota.getNota());
        dto.setObservacoes(nota.getObservacoes());
        dto.setDataLancamento(nota.getDataLancamento());
        dto.setNomeAluno(nota.getMatricula().getAluno().getNome());
        dto.setNomeDisciplina(nota.getMatricula().getDisciplina().getNome());
        dto.setTituloAvaliacao(nota.getAvaliacao().getTitulo());
        dto.setTipoAvaliacao(nota.getAvaliacao().getTipo().getDescricao());
        dto.setPesoAvaliacao(nota.getAvaliacao().getPeso());

        return dto;
    }


    public Nota toEntity(NotaDTO dto, Matricula matricula, Avaliacao avaliacao) {
        if (dto == null) {
            return null;
        }

        Nota nota = new Nota();
        nota.setId(dto.getId());
        nota.setMatricula(matricula);
        nota.setAvaliacao(avaliacao);
        nota.setNota(dto.getNota());
        nota.setObservacoes(dto.getObservacoes());

        return nota;
    }


    public void updateEntityFromDTO(NotaDTO dto, Nota nota) {
        if (dto.getNota() != null) {
            nota.setNota(dto.getNota());
        }
        if (dto.getObservacoes() != null) {
            nota.setObservacoes(dto.getObservacoes());
        }
    }


    public List<NotaDTO> toDTOList(List<Nota> notas) {
        return notas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}