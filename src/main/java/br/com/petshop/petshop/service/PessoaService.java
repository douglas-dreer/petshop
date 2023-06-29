package br.com.petshop.petshop.service;

import br.com.petshop.petshop.model.Pessoa;

import java.util.List;
import java.util.UUID;

public interface PessoaService {
    List<Pessoa> listar();
    Pessoa buscarPorId(UUID id);
    List<Pessoa> buscarPorId(List<UUID> uuidList);
    List<Pessoa> listarPorNome(String nome);
    List<Pessoa> salvar(List<Pessoa> modelList);
    Pessoa salvar(Pessoa model);
    Pessoa editar(Pessoa model);
    void excluir(UUID id);
    List<Pessoa> listarPorStatus(boolean isAtivo);
}
