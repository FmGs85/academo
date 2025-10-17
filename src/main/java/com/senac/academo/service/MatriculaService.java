package com.senac.academo.service;

import com.senac.academo.exception.ResourceNotFoundException;
import com.senac.academo.mapper.MatriculaMapper;
import com.senac.academo.model.dto.MatriculaDTO;
import com.senac.academo.model.entity.Disciplina;
import com.senac.academo.model.entity.Matricula;
import com.senac.academo.model.entity.Usuario;
import com.senac.academo.model.enums.StatusMatricula;
import com.senac.academo.repository.DisciplinaRepository;
import com.senac.academo.repository.MatriculaRepository;
import com.senac.academo.repository.NotaRepository;
import com.senac.academo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private MatriculaMapper matriculaMapper;


    @Transactional(readOnly = true)
    public List<MatriculaDTO> findAll() {
        List<Matricula> matriculas = matriculaRepository.findAll();
        return matriculaMapper.toDTOList(matriculas);
    }


    @Transactional(readOnly = true)
    public MatriculaDTO findById(Integer id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula não encontrada com ID: " + id));

        MatriculaDTO dto = matriculaMapper.toDTO(matricula);

        // Adicionar média ponderada
        BigDecimal media = notaRepository.calcularMediaPonderada(id);
        dto.setMediaPonderada(media);

        // Determinar situação
        if (media != null) {
            if (media.compareTo(new BigDecimal("7.00")) >= 0) {
                dto.setSituacao("Aprovado");
            } else if (media.compareTo(new BigDecimal("4.00")) >= 0) {
                dto.setSituacao("Recuperação");
            } else {
                dto.setSituacao("Reprovado");
            }
        }

        return dto;
    }


    public MatriculaDTO create(MatriculaDTO matriculaDTO) {
        // Validar se aluno existe
        Usuario aluno = usuarioRepository.findById(matriculaDTO.getAlunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        // Validar se disciplina existe
        Disciplina disciplina = disciplinaRepository.findById(matriculaDTO.getDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada"));

        // Validar se já existe matrícula
        if (matriculaRepository.existsByAlunoIdAndDisciplinaIdAndPeriodo(
                matriculaDTO.getAlunoId(),
                matriculaDTO.getDisciplinaId(),
                matriculaDTO.getPeriodo())) {
            throw new IllegalArgumentException(
                    "Aluno já está matriculado nesta disciplina no período " + matriculaDTO.getPeriodo());
        }

        Matricula matricula = matriculaMapper.toEntity(matriculaDTO, aluno, disciplina);
        Matricula savedMatricula = matriculaRepository.save(matricula);
        return matriculaMapper.toDTO(savedMatricula);
    }


    public MatriculaDTO update(Integer id, MatriculaDTO matriculaDTO) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula não encontrada com ID: " + id));

        matriculaMapper.updateEntityFromDTO(matriculaDTO, matricula);
        Matricula updatedMatricula = matriculaRepository.save(matricula);
        return matriculaMapper.toDTO(updatedMatricula);
    }


    public void delete(Integer id) {
        if (!matriculaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Matrícula não encontrada com ID: " + id);
        }
        matriculaRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public List<MatriculaDTO> findByAluno(Integer alunoId) {
        List<Matricula> matriculas = matriculaRepository.findByAlunoId(alunoId);
        return matriculaMapper.toDTOList(matriculas);
    }


    @Transactional(readOnly = true)
    public List<MatriculaDTO> findByDisciplina(Integer disciplinaId) {
        List<Matricula> matriculas = matriculaRepository.findByDisciplinaId(disciplinaId);
        return matriculaMapper.toDTOList(matriculas);
    }


    @Transactional(readOnly = true)
    public List<MatriculaDTO> findByPeriodo(String periodo) {
        List<Matricula> matriculas = matriculaRepository.findByPeriodo(periodo);
        return matriculaMapper.toDTOList(matriculas);
    }


    @Transactional(readOnly = true)
    public List<MatriculaDTO> findByStatus(StatusMatricula status) {
        List<Matricula> matriculas = matriculaRepository.findByStatus(status);
        return matriculaMapper.toDTOList(matriculas);
    }


    @Transactional(readOnly = true)
    public List<MatriculaDTO> findMatriculasAtivasByAluno(Integer alunoId) {
        List<Matricula> matriculas = matriculaRepository.findMatriculasAtivasByAlunoId(alunoId);
        return matriculaMapper.toDTOList(matriculas);
    }


    @Transactional(readOnly = true)
    public List<MatriculaDTO> findByAlunoAndPeriodo(Integer alunoId, String periodo) {
        List<Matricula> matriculas = matriculaRepository.findByAlunoIdAndPeriodo(alunoId, periodo);
        return matriculaMapper.toDTOList(matriculas);
    }


    public MatriculaDTO trancarMatricula(Integer id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula não encontrada com ID: " + id));

        matricula.setStatus(StatusMatricula.TRANCADA);
        Matricula updatedMatricula = matriculaRepository.save(matricula);
        return matriculaMapper.toDTO(updatedMatricula);
    }


    public MatriculaDTO reativarMatricula(Integer id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula não encontrada com ID: " + id));

        matricula.setStatus(StatusMatricula.ATIVA);
        Matricula updatedMatricula = matriculaRepository.save(matricula);
        return matriculaMapper.toDTO(updatedMatricula);
    }


    @Transactional(readOnly = true)
    public Long contarMatriculasAtivas(Integer alunoId) {
        return matriculaRepository.countMatriculasAtivasByAlunoId(alunoId);
    }
}