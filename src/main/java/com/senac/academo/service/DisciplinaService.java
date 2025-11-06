package com.senac.academo.service;

import com.senac.academo.exception.ResourceNotFoundException;
import com.senac.academo.mapper.DisciplinaMapper;
import com.senac.academo.model.dto.DisciplinaDTO;
import com.senac.academo.model.entity.Disciplina;
import com.senac.academo.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private DisciplinaMapper disciplinaMapper;

    @Transactional(readOnly = true)
    public List<DisciplinaDTO> findAll() {
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        return disciplinaMapper.toDTOList(disciplinas);
    }

    @Transactional(readOnly = true)
    public DisciplinaDTO findById(Integer id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com ID: " + id));

        DisciplinaDTO dto = disciplinaMapper.toDTO(disciplina);

        // Adicionar total de alunos
        Long totalAlunos = disciplinaRepository.countAlunosMatriculados(id);
        dto.setTotalAlunos(totalAlunos);

        return dto;
    }

    @Transactional(readOnly = true)
    public DisciplinaDTO findByCodigo(String codigo) {
        Disciplina disciplina = disciplinaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com código: " + codigo));
        return disciplinaMapper.toDTO(disciplina);
    }

    public DisciplinaDTO create(DisciplinaDTO disciplinaDTO) {
        // Validar se código já existe
        if (disciplinaRepository.existsByCodigo(disciplinaDTO.getCodigo())) {
            throw new IllegalArgumentException("Código de disciplina já cadastrado: " + disciplinaDTO.getCodigo());
        }

        Disciplina disciplina = disciplinaMapper.toEntity(disciplinaDTO);
        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);
        return disciplinaMapper.toDTO(savedDisciplina);
    }

    public DisciplinaDTO update(Integer id, DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com ID: " + id));

        // Validar código se foi alterado
        if (!disciplina.getCodigo().equals(disciplinaDTO.getCodigo())) {
            if (disciplinaRepository.existsByCodigo(disciplinaDTO.getCodigo())) {
                throw new IllegalArgumentException("Código de disciplina já cadastrado: " + disciplinaDTO.getCodigo());
            }
        }

        disciplinaMapper.updateEntityFromDTO(disciplinaDTO, disciplina);
        Disciplina updatedDisciplina = disciplinaRepository.save(disciplina);
        return disciplinaMapper.toDTO(updatedDisciplina);
    }

    public void delete(Integer id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com ID: " + id));

        disciplina.setAtivo(false);
        disciplinaRepository.save(disciplina);
    }

    @Transactional(readOnly = true)
    public List<DisciplinaDTO> findAtivas() {
        List<Disciplina> disciplinas = disciplinaRepository.findByAtivo(true);
        return disciplinaMapper.toDTOList(disciplinas);
    }

    @Transactional(readOnly = true)
    public List<DisciplinaDTO> findByNome(String nome) {
        List<Disciplina> disciplinas = disciplinaRepository.findByNomeContaining(nome);
        return disciplinaMapper.toDTOList(disciplinas);
    }

    @Transactional(readOnly = true)
    public List<DisciplinaDTO> findByProfessor(Integer professorId) {
        List<Disciplina> disciplinas = disciplinaRepository.findByProfessorId(professorId);
        return disciplinaMapper.toDTOList(disciplinas);
    }

    @Transactional(readOnly = true)
    public List<DisciplinaDTO> findByAluno(Integer alunoId) {
        List<Disciplina> disciplinas = disciplinaRepository.findByAlunoId(alunoId);
        return disciplinaMapper.toDTOList(disciplinas);
    }

    @Transactional(readOnly = true)
    public Long contarAlunosMatriculados(Integer disciplinaId) {
        return disciplinaRepository.countAlunosMatriculados(disciplinaId);
    }
}