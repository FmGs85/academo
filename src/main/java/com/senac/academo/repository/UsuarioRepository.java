package com.senac.academo.repository;

import com.senac.academo.model.entity.Usuario;
import com.senac.academo.model.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {


    Optional<Usuario> findByEmail(String email);


    boolean existsByEmail(String email);


    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);


    List<Usuario> findByAtivo(Boolean ativo);


    List<Usuario> findByTipoUsuarioAndAtivo(TipoUsuario tipoUsuario, Boolean ativo);


    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario = 'PROFESSOR' AND u.ativo = true")
    List<Usuario> findProfessoresAtivos();


    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario = 'ALUNO' AND u.ativo = true")
    List<Usuario> findAlunosAtivos();


    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNomeContaining(@Param("nome") String nome);


    @Query("SELECT DISTINCT u FROM Usuario u " +
            "JOIN u.matriculas m " +
            "WHERE m.disciplina.id = :disciplinaId " +
            "AND m.status = 'ATIVA' " +
            "AND u.ativo = true")
    List<Usuario> findAlunosByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);


    @Query("SELECT DISTINCT u FROM Usuario u " +
            "JOIN u.disciplinasLecionadas dp " +
            "WHERE dp.disciplina.id = :disciplinaId " +
            "AND dp.ativo = true " +
            "AND u.ativo = true")
    List<Usuario> findProfessoresByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);
}
