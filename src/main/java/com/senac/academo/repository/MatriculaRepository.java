package com.senac.academo.repository;

import com.senac.academo.model.entity.Matricula;
import com.senac.academo.model.enums.StatusMatricula;
import com.senac.academo.model.enums.SituacaoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {

    /**
     * Busca todas as matrículas de um aluno
     */
    List<Matricula> findByAlunoId(Integer alunoId);

    /**
     * Busca todas as matrículas de uma disciplina
     */
    List<Matricula> findByDisciplinaId(Integer disciplinaId);

    /**
     * Busca matrículas por status
     */
    List<Matricula> findByStatus(StatusMatricula status);

    /**
     * Busca matrículas por situação
     */
    List<Matricula> findBySituacao(SituacaoMatricula situacao);

    /**
     * Busca matrículas de um aluno com um status específico
     */
    List<Matricula> findByAlunoIdAndStatus(Integer alunoId, StatusMatricula status);

    /**
     * Busca matrículas de uma disciplina com um status específico
     */
    List<Matricula> findByDisciplinaIdAndStatus(Integer disciplinaId, StatusMatricula status);

    /**
     * Busca uma matrícula específica de um aluno em uma disciplina
     */
    Optional<Matricula> findByAlunoIdAndDisciplinaId(Integer alunoId, Integer disciplinaId);

    /**
     * Verifica se existe matrícula de um aluno em uma disciplina
     */
    boolean existsByAlunoIdAndDisciplinaId(Integer alunoId, Integer disciplinaId);

    /**
     * Verifica se existe matrícula ativa de um aluno em uma disciplina
     */
    boolean existsByAlunoIdAndDisciplinaIdAndStatus(Integer alunoId, Integer disciplinaId, StatusMatricula status);

    /**
     * Conta quantas matrículas ativas um aluno possui
     */
    long countByAlunoIdAndStatus(Integer alunoId, StatusMatricula status);

    /**
     * Busca todas as matrículas ativas
     */
    List<Matricula> findByStatusOrderByDataMatriculaDesc(StatusMatricula status);
}