package com.senac.academo.repository;

import com.senac.academo.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Busca uma role pelo nome
     * @param nome Nome da role (ex: ROLE_ALUNO, ROLE_PROFESSOR, ROLE_ADMIN)
     * @return Optional com a role encontrada
     */
    Optional<Role> findByNome(String nome);

    /**
     * Verifica se existe uma role com o nome especificado
     * @param nome Nome da role
     * @return true se existe, false caso contrário
     */
    boolean existsByNome(String nome);
}