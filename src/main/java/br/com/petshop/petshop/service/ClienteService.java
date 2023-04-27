package br.com.petshop.petshop.service;

import br.com.petshop.petshop.model.Cliente;

import java.util.List;
import java.util.UUID;

public interface ClienteService {
    List<Cliente> listar();

    Cliente buscarPorId(UUID id);
    List<Cliente> buscarPorId(List<UUID> uuidList);
    List<Cliente> listarPorNome(String nome);
    List<Cliente> salvar(List<Cliente> modelList);
    Cliente salvar(Cliente model);
    Cliente editar(Cliente model);
    Cliente excluir(UUID id);
}
