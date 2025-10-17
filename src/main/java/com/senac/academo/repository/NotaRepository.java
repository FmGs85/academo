package com.senac.academo.repository;

import com.senac.academo.model.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {


    List<Nota> findByMatriculaId(Integer matriculaId);


    List<Nota> findByAvaliacaoId(Integer avaliacaoId);


    Optional<Nota> findByMatriculaIdAndAvaliacaoId(Integer matriculaId, Integer avaliacaoId);


    boolean existsByMatriculaIdAndAvaliacaoId(Integer matriculaId, Integer avaliacaoId);


    @Query("SELECT n FROM Nota n " +
            "WHERE n.matricula.aluno.id = :alunoId " +
            "AND n.matricula.disciplina.id = :disciplinaId")
    List<Nota> findByAlunoIdAndDisciplinaId(
            @Param("alunoId") Integer alunoId,
            @Param("disciplinaId") Integer disciplinaId);


    @Query("SELECT AVG(n.nota) FROM Nota n " +
            "WHERE n.matricula.aluno.id = :alunoId " +
            "AND n.matricula.disciplina.id = :disciplinaId")
    BigDecimal calcularMediaSimples(
            @Param("alunoId") Integer alunoId,
            @Param("disciplinaId") Integer disciplinaId);


    @Query("SELECT SUM(n.nota * n.avaliacao.peso) / SUM(n.avaliacao.peso) " +
            "FROM Nota n " +
            "WHERE n.matricula.id = :matriculaId")
    BigDecimal calcularMediaPonderada(@Param("matriculaId") Integer matriculaId);


    @Query("SELECT n FROM Nota n " +
            "WHERE n.matricula.aluno.id = :alunoId " +
            "ORDER BY n.dataLancamento DESC")
    List<Nota> findByAlunoId(@Param("alunoId") Integer alunoId);


    @Query("SELECT n FROM Nota n " +
            "ORDER BY n.dataLancamento DESC")
    List<Nota> findTopByOrderByDataLancamentoDesc();


    Long countByAvaliacaoId(Integer avaliacaoId);


    @Query("SELECT n FROM Nota n " +
            "JOIN FETCH n.matricula m " +
            "JOIN FETCH m.aluno a " +
            "JOIN FETCH m.disciplina d " +
            "JOIN FETCH n.avaliacao av " +
            "WHERE n.matricula.id = :matriculaId")
    List<Nota> findByMatriculaIdWithDetails(@Param("matriculaId") Integer matriculaId);
}