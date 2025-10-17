package com.senac.academo.mapper;

import com.senac.academo.model.dto.MatriculaDTO;
import com.senac.academo.model.entity.Disciplina;
import com.senac.academo.model.entity.Matricula;
import com.senac.academo.model.entity.Usuario;
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
        dto.setAlunoId(matricula.getAluno().getId());
        dto.setDisciplinaId(matricula.getDisciplina().getId());
        dto.setDataMatricula(matricula.getDataMatricula());
        dto.setStatus(matricula.getStatus());
        dto.setPeriodo(matricula.getPeriodo());

        // Dados adicionais
        dto.setNomeAluno(matricula.getAluno().getNome());
        dto.setEmailAluno(matricula.getAluno().getEmail());
        dto.setNomeDisciplina(matricula.getDisciplina().getNome());
        dto.setCodigoDisciplina(matricula.getDisciplina().getCodigo());

        return dto;
    }


    public Matricula toEntity(MatriculaDTO dto, Usuario aluno, Disciplina disciplina) {
        if (dto == null) {
            return null;
        }

        Matricula matricula = new Matricula();
        matricula.setId(dto.getId());
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setStatus(dto.getStatus());
        matricula.setPeriodo(dto.getPeriodo());

        return matricula;
    }


    public void updateEntityFromDTO(MatriculaDTO dto, Matricula matricula) {
        if (dto.getStatus() != null) {
            matricula.setStatus(dto.getStatus());
        }
        if (dto.getPeriodo() != null) {
            matricula.setPeriodo(dto.getPeriodo());
        }
    }


    public List<MatriculaDTO> toDTOList(List<Matricula> matriculas) {
        return matriculas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}