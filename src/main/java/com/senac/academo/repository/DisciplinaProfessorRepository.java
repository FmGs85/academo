package com.senac.academo.repository;

import com.senac.academo.model.entity.DisciplinaProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplinaProfessorRepository extends JpaRepository<DisciplinaProfessor, Integer> {


    List<DisciplinaProfessor> findByProfessorId(Integer professorId);


    List<DisciplinaProfessor> findByDisciplinaId(Integer disciplinaId);


    Optional<DisciplinaProfessor> findByProfessorIdAndDisciplinaId(
            Integer professorId, Integer disciplinaId);


    boolean existsByProfessorIdAndDisciplinaId(
            Integer professorId, Integer disciplinaId);


    @Query("SELECT dp FROM DisciplinaProfessor dp " +
            "WHERE dp.professor.id = :professorId " +
            "AND dp.ativo = true")
    List<DisciplinaProfessor> findAtivasByProfessorId(@Param("professorId") Integer professorId);


    @Query("SELECT dp FROM DisciplinaProfessor dp " +
            "WHERE dp.disciplina.id = :disciplinaId " +
            "AND dp.ativo = true")
    List<DisciplinaProfessor> findAtivasByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);


    @Query("SELECT dp FROM DisciplinaProfessor dp " +
            "JOIN FETCH dp.professor p " +
            "JOIN FETCH dp.disciplina d " +
            "WHERE dp.disciplina.id = :disciplinaId " +
            "AND dp.ativo = true " +
            "AND p.ativo = true")
    List<DisciplinaProfessor> findProfessoresAtivosByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);


    @Query("SELECT COUNT(dp) FROM DisciplinaProfessor dp " +
            "WHERE dp.professor.id = :professorId " +
            "AND dp.ativo = true")
    Long countDisciplinasByProfessorId(@Param("professorId") Integer professorId);


    void deleteByProfessorIdAndDisciplinaId(Integer professorId, Integer disciplinaId);
}