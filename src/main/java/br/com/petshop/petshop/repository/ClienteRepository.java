package br.com.petshop.petshop.repository;

import br.com.petshop.petshop.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {

    List<ClienteEntity> findClienteEntitiesByNomeContainsIgnoreCase(String nome);
}
