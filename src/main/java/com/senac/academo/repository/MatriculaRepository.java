package com.senac.academo.repository;

import com.senac.academo.model.entity.Matricula;
import com.senac.academo.model.enums.StatusMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {


    List<Matricula> findByAlunoId(Integer alunoId);


    List<Matricula> findByDisciplinaId(Integer disciplinaId);


    List<Matricula> findByPeriodo(String periodo);


    Optional<Matricula> findByAlunoIdAndDisciplinaIdAndPeriodo(
            Integer alunoId, Integer disciplinaId, String periodo);


    List<Matricula> findByStatus(StatusMatricula status);


    @Query("SELECT m FROM Matricula m WHERE m.aluno.id = :alunoId AND m.status = 'ATIVA'")
    List<Matricula> findMatriculasAtivasByAlunoId(@Param("alunoId") Integer alunoId);


    @Query("SELECT m FROM Matricula m " +
            "WHERE m.aluno.id = :alunoId " +
            "AND m.periodo = :periodo")
    List<Matricula> findByAlunoIdAndPeriodo(
            @Param("alunoId") Integer alunoId,
            @Param("periodo") String periodo);


    @Query("SELECT m FROM Matricula m " +
            "WHERE m.disciplina.id = :disciplinaId " +
            "AND m.periodo = :periodo " +
            "AND m.status = 'ATIVA'")
    List<Matricula> findByDisciplinaIdAndPeriodo(
            @Param("disciplinaId") Integer disciplinaId,
            @Param("periodo") String periodo);


    boolean existsByAlunoIdAndDisciplinaIdAndPeriodo(
            Integer alunoId, Integer disciplinaId, String periodo);


    @Query("SELECT COUNT(m) FROM Matricula m " +
            "WHERE m.aluno.id = :alunoId " +
            "AND m.status = 'ATIVA'")
    Long countMatriculasAtivasByAlunoId(@Param("alunoId") Integer alunoId);


    @Query("SELECT DISTINCT m FROM Matricula m " +
            "LEFT JOIN FETCH m.notas n " +
            "WHERE m.id = :matriculaId")
    Optional<Matricula> findByIdWithNotas(@Param("matriculaId") Integer matriculaId);
}