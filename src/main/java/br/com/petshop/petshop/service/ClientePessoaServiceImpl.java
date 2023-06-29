package br.com.petshop.petshop.service;

import br.com.petshop.petshop.entity.ClientePessoaEntity;
import br.com.petshop.petshop.model.ClientePessoa;
import br.com.petshop.petshop.repository.ClientePessoaRepository;
import br.com.petshop.petshop.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientePessoaServiceImpl implements ClientePessoaService {
    private final ClientePessoaRepository repository;

    public ClientePessoaServiceImpl(ClientePessoaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ClientePessoa> listar() {
        return null;
    }

    @Override
    public ClientePessoa buscarPorId(UUID id) {
        return null;
    }

    @Override
    public List<ClientePessoa> buscarPorId(List<UUID> uuidList) {
        return null;
    }

    @Override
    public List<ClientePessoa> salvar(List<ClientePessoa> modelList) {
        return null;
    }

    @Override
    public ClientePessoa salvar(ClientePessoa model) {
        ClientePessoaEntity entity = MapperUtil.convertTo(model, ClientePessoaEntity.class);
        return MapperUtil.convertTo(repository.save(entity), ClientePessoa.class);
    }

    @Override
    public ClientePessoa editar(ClientePessoa model) {
        return null;
    }

    @Override
    public ClientePessoa excluir(UUID id) {
        return null;
    }
}
