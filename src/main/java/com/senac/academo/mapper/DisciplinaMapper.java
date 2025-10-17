package com.senac.academo.mapper;

import com.senac.academo.model.dto.DisciplinaDTO;
import com.senac.academo.model.entity.Disciplina;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DisciplinaMapper {


    public DisciplinaDTO toDTO(Disciplina disciplina) {
        if (disciplina == null) {
            return null;
        }

        DisciplinaDTO dto = new DisciplinaDTO();
        dto.setId(disciplina.getId());
        dto.setNome(disciplina.getNome());
        dto.setCodigo(disciplina.getCodigo());
        dto.setDescricao(disciplina.getDescricao());
        dto.setCargaHoraria(disciplina.getCargaHoraria());
        dto.setAtivo(disciplina.getAtivo());
        dto.setDataCriacao(disciplina.getDataCriacao());

        return dto;
    }


    public Disciplina toEntity(DisciplinaDTO dto) {
        if (dto == null) {
            return null;
        }

        Disciplina disciplina = new Disciplina();
        disciplina.setId(dto.getId());
        disciplina.setNome(dto.getNome());
        disciplina.setCodigo(dto.getCodigo());
        disciplina.setDescricao(dto.getDescricao());
        disciplina.setCargaHoraria(dto.getCargaHoraria());
        disciplina.setAtivo(dto.getAtivo());

        return disciplina;
    }


    public void updateEntityFromDTO(DisciplinaDTO dto, Disciplina disciplina) {
        if (dto.getNome() != null) {
            disciplina.setNome(dto.getNome());
        }
        if (dto.getCodigo() != null) {
            disciplina.setCodigo(dto.getCodigo());
        }
        if (dto.getDescricao() != null) {
            disciplina.setDescricao(dto.getDescricao());
        }
        if (dto.getCargaHoraria() != null) {
            disciplina.setCargaHoraria(dto.getCargaHoraria());
        }
        if (dto.getAtivo() != null) {
            disciplina.setAtivo(dto.getAtivo());
        }
    }


    public List<DisciplinaDTO> toDTOList(List<Disciplina> disciplinas) {
        return disciplinas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}