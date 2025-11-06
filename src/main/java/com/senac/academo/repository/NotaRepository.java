package com.senac.academo.repository;

import com.senac.academo.model.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {

    /**
     * Busca todas as notas de uma matrícula
     */
    List<Nota> findByMatriculaId(Integer matriculaId);

    /**
     * Busca todas as notas de uma avaliação
     */
    List<Nota> findByAvaliacaoId(Integer avaliacaoId);

    /**
     * Busca nota específica por matrícula e avaliação
     */
    Optional<Nota> findByMatriculaIdAndAvaliacaoId(Integer matriculaId, Integer avaliacaoId);

    /**
     * Verifica se existe nota para uma matrícula em uma avaliação
     */
    boolean existsByMatriculaIdAndAvaliacaoId(Integer matriculaId, Integer avaliacaoId);

    /**
     * Busca todas as notas de um aluno (através da matrícula)
     */
    List<Nota> findByMatriculaAlunoId(Integer alunoId);

    /**
     * Busca todas as notas de uma disciplina (através da matrícula)
     */
    List<Nota> findByMatriculaDisciplinaId(Integer disciplinaId);

    /**
     * Busca notas de um aluno em uma disciplina específica
     */
    List<Nota> findByMatriculaAlunoIdAndMatriculaDisciplinaId(Integer alunoId, Integer disciplinaId);

    /**
     * Deleta todas as notas de uma matrícula
     */
    void deleteByMatriculaId(Integer matriculaId);

    /**
     * Deleta todas as notas de uma avaliação
     */
    void deleteByAvaliacaoId(Integer avaliacaoId);

    /**
     * Conta quantas notas um aluno tem
     */
    long countByMatriculaAlunoId(Integer alunoId);

    /**
     * Conta quantas notas existem para uma avaliação
     */
    long countByAvaliacaoId(Integer avaliacaoId);  // ADICIONE ESTA LINHA
}