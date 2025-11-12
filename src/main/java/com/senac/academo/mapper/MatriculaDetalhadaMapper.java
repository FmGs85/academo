package com.senac.academo.mapper;

import com.senac.academo.model.dto.MatriculaDetalhadaDTO;
import com.senac.academo.model.entity.DisciplinaProfessor;
import com.senac.academo.model.entity.Matricula;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatriculaDetalhadaMapper {

    public MatriculaDetalhadaDTO toDTO(Matricula matricula) {
        if (matricula == null) {
            return null;
        }

        MatriculaDetalhadaDTO dto = new MatriculaDetalhadaDTO();
        dto.setId(matricula.getId());
        dto.setDataMatricula(matricula.getDataMatricula());
        dto.setStatus(matricula.getStatus());
        dto.setSituacao(matricula.getSituacao());
        dto.setMediaFinal(matricula.getMediaFinal());
        dto.setCargaHorariaPresente(matricula.getCargaHorariaPresente());

        // Informações da disciplina
        if (matricula.getDisciplina() != null) {
            MatriculaDetalhadaDTO.DisciplinaInfo disciplinaInfo = new MatriculaDetalhadaDTO.DisciplinaInfo();
            disciplinaInfo.setId(matricula.getDisciplina().getId());
            disciplinaInfo.setCodigo(matricula.getDisciplina().getCodigo());
            disciplinaInfo.setNome(matricula.getDisciplina().getNome());
            disciplinaInfo.setDescricao(matricula.getDisciplina().getDescricao());
            disciplinaInfo.setCargaHoraria(matricula.getDisciplina().getCargaHoraria());
            disciplinaInfo.setCreditos(matricula.getDisciplina().getCreditos());

            // Buscar professor da disciplina (pega o primeiro professor ativo)
            if (matricula.getDisciplina().getProfessores() != null &&
                    !matricula.getDisciplina().getProfessores().isEmpty()) {

                DisciplinaProfessor dp = matricula.getDisciplina().getProfessores().stream()
                        .filter(DisciplinaProfessor::getAtivo)
                        .findFirst()
                        .orElse(null);

                if (dp != null && dp.getProfessor() != null) {
                    MatriculaDetalhadaDTO.ProfessorInfo professorInfo = new MatriculaDetalhadaDTO.ProfessorInfo();
                    professorInfo.setId(dp.getProfessor().getId());
                    professorInfo.setNome(dp.getProfessor().getNome());
                    professorInfo.setEmail(dp.getProfessor().getEmail());
                    disciplinaInfo.setProfessor(professorInfo);
                }
            }

            dto.setDisciplina(disciplinaInfo);
        }

        return dto;
    }

    public List<MatriculaDetalhadaDTO> toDTOList(List<Matricula> matriculas) {
        if (matriculas == null) {
            return null;
        }
        return matriculas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}