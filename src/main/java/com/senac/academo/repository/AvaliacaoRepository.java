package com.senac.academo.repository;

import com.senac.academo.model.entity.Avaliacao;
import com.senac.academo.model.enums.TipoAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {


    List<Avaliacao> findByDisciplinaId(Integer disciplinaId);


    List<Avaliacao> findByTipo(TipoAvaliacao tipo);


    List<Avaliacao> findByDisciplinaIdAndTipo(Integer disciplinaId, TipoAvaliacao tipo);


    List<Avaliacao> findByDataAvaliacao(LocalDate dataAvaliacao);


    @Query("SELECT a FROM Avaliacao a " +
            "WHERE a.dataAvaliacao BETWEEN :dataInicio AND :dataFim")
    List<Avaliacao> findByDataAvaliacaoBetween(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);


    @Query("SELECT a FROM Avaliacao a " +
            "WHERE a.disciplina.id = :disciplinaId " +
            "AND a.dataAvaliacao >= CURRENT_DATE " +
            "ORDER BY a.dataAvaliacao ASC")
    List<Avaliacao> findAvaliacoesFuturasByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);


    @Query("SELECT a FROM Avaliacao a " +
            "WHERE a.disciplina.id = :disciplinaId " +
            "AND a.dataAvaliacao < CURRENT_DATE " +
            "ORDER BY a.dataAvaliacao DESC")
    List<Avaliacao> findAvaliacoesRealizadasByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);


    Long countByDisciplinaId(Integer disciplinaId);


    @Query("SELECT a FROM Avaliacao a " +
            "WHERE a.disciplina.id = :disciplinaId " +
            "ORDER BY a.dataAvaliacao ASC")
    List<Avaliacao> findByDisciplinaIdOrderByDataAvaliacao(@Param("disciplinaId") Integer disciplinaId);
}