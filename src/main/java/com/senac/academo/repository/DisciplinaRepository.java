package com.senac.academo.repository;

import com.senac.academo.model.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {

    Optional<Disciplina> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Disciplina> findByAtivo(Boolean ativo);

    @Query("SELECT d FROM Disciplina d WHERE LOWER(d.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Disciplina> findByNomeContaining(@Param("nome") String nome);

    @Query("SELECT DISTINCT d FROM Disciplina d " +
            "JOIN d.professores dp " +
            "WHERE dp.professor.id = :professorId " +
            "AND dp.ativo = true " +
            "AND d.ativo = true")
    List<Disciplina> findByProfessorId(@Param("professorId") Integer professorId);

    @Query("SELECT DISTINCT d FROM Disciplina d " +
            "JOIN d.matriculas m " +
            "WHERE m.aluno.id = :alunoId " +
            "AND m.status = 'ATIVA' " +
            "AND d.ativo = true")
    List<Disciplina> findByAlunoId(@Param("alunoId") Integer alunoId);

    @Query("SELECT COUNT(DISTINCT m.aluno.id) FROM Matricula m " +
            "WHERE m.disciplina.id = :disciplinaId " +
            "AND m.status = 'ATIVA'")
    Long countAlunosMatriculados(@Param("disciplinaId") Integer disciplinaId);
}