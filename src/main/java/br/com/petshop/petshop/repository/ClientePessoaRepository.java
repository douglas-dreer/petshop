package br.com.petshop.petshop.repository;

import br.com.petshop.petshop.entity.ClientePessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientePessoaRepository extends JpaRepository<ClientePessoaEntity, UUID> {
}
