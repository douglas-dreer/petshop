package br.com.petshop.petshop.repository;

import br.com.petshop.petshop.entity.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, UUID> {
    Optional<PessoaEntity> findById(UUID id);
    List<PessoaEntity> findClienteEntitiesByNomeContainsIgnoreCase(String nome);

    List<PessoaEntity> findAllByIsAtivo(boolean isAtivo);
}
