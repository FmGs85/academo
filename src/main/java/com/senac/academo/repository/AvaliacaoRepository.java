package com.senac.academo.repository;

import com.senac.academo.model.entity.Avaliacao;
import com.senac.academo.model.enums.TipoAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

    /**
     * Busca todas as avaliações de uma disciplina
     */
    List<Avaliacao> findByDisciplinaId(Integer disciplinaId);

    /**
     * Busca avaliações ativas de uma disciplina
     */
    List<Avaliacao> findByDisciplinaIdAndAtivoTrue(Integer disciplinaId);

    /**
     * Busca avaliações por tipo
     */
    List<Avaliacao> findByTipo(TipoAvaliacao tipo);

    /**
     * Busca avaliações de uma disciplina por tipo
     */
    List<Avaliacao> findByDisciplinaIdAndTipo(Integer disciplinaId, TipoAvaliacao tipo);

    /**
     * Busca avaliações por data específica
     */
    List<Avaliacao> findByData(LocalDate data);

    /**
     * Busca avaliações por período de data
     */
    List<Avaliacao> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca avaliações de uma disciplina em um período
     */
    List<Avaliacao> findByDisciplinaIdAndDataBetween(Integer disciplinaId, LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca avaliações futuras de uma disciplina
     */
    List<Avaliacao> findByDisciplinaIdAndDataGreaterThanEqualOrderByDataAsc(Integer disciplinaId, LocalDate data);

    /**
     * Busca avaliações passadas de uma disciplina
     */
    List<Avaliacao> findByDisciplinaIdAndDataLessThanOrderByDataDesc(Integer disciplinaId, LocalDate data);

    /**
     * Busca avaliações de uma disciplina ordenadas por data
     */
    List<Avaliacao> findByDisciplinaIdOrderByDataAsc(Integer disciplinaId);

    /**
     * Busca avaliações ativas ordenadas por data
     */
    List<Avaliacao> findByAtivoTrueOrderByDataAsc();

    /**
     * Conta quantas avaliações uma disciplina tem
     */
    long countByDisciplinaId(Integer disciplinaId);

    /**
     * Deleta todas as avaliações de uma disciplina
     */
    void deleteByDisciplinaId(Integer disciplinaId);
}