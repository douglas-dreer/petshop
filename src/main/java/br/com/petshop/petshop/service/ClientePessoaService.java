package br.com.petshop.petshop.service;


import br.com.petshop.petshop.model.ClientePessoa;

import java.util.List;
import java.util.UUID;

public interface ClientePessoaService {
    List<ClientePessoa> listar();
    ClientePessoa buscarPorId(UUID id);
    List<ClientePessoa> buscarPorId(List<UUID> uuidList);

    List<ClientePessoa> salvar(List<ClientePessoa> modelList);
    ClientePessoa salvar(ClientePessoa model);
    ClientePessoa editar(ClientePessoa model);
    ClientePessoa excluir(UUID id);
}
