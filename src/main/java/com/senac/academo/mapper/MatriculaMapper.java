package com.senac.academo.mapper;

import com.senac.academo.model.dto.MatriculaDTO;
import com.senac.academo.model.entity.Matricula;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatriculaMapper {

    public MatriculaDTO toDTO(Matricula matricula) {
        if (matricula == null) {
            return null;
        }

        MatriculaDTO dto = new MatriculaDTO();
        dto.setId(matricula.getId());
        dto.setDataMatricula(matricula.getDataMatricula());
        dto.setStatus(matricula.getStatus());  // Removido o .name()
        dto.setSituacao(matricula.getSituacao());  // Removido o .name()
        dto.setMediaFinal(matricula.getMediaFinal());
        dto.setCargaHorariaPresente(matricula.getCargaHorariaPresente());

        // Informações do aluno
        if (matricula.getAluno() != null) {
            dto.setAlunoId(matricula.getAluno().getId());
            dto.setAlunoNome(matricula.getAluno().getNome());
        }

        // Informações da disciplina
        if (matricula.getDisciplina() != null) {
            dto.setDisciplinaId(matricula.getDisciplina().getId());
            dto.setDisciplinaNome(matricula.getDisciplina().getNome());
        }

        return dto;
    }

    public List<MatriculaDTO> toDTOList(List<Matricula> matriculas) {
        if (matriculas == null) {
            return null;
        }
        return matriculas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Matricula toEntity(MatriculaDTO dto) {
        if (dto == null) {
            return null;
        }

        Matricula matricula = new Matricula();
        matricula.setId(dto.getId());
        matricula.setDataMatricula(dto.getDataMatricula());
        matricula.setStatus(dto.getStatus());  // Agora aceita o enum direto
        matricula.setSituacao(dto.getSituacao());  // Agora aceita o enum direto
        matricula.setMediaFinal(dto.getMediaFinal());
        matricula.setCargaHorariaPresente(dto.getCargaHorariaPresente());

        // Relacionamentos (aluno e disciplina) serão setados no Service

        return matricula;
    }

    public List<Matricula> toEntityList(List<MatriculaDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}