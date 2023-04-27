package br.com.petshop.petshop.service;

import br.com.petshop.petshop.entity.ClienteEntity;
import br.com.petshop.petshop.model.Cliente;
import br.com.petshop.petshop.repository.ClienteRepository;
import br.com.petshop.petshop.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar() {
        return MapperUtil.mapList(clienteRepository.findAll(), Cliente.class);
    }

    @Override
    public List<Cliente> listarPorNome(String nome) {
        return MapperUtil.mapList(clienteRepository.findClienteEntitiesByNomeContainsIgnoreCase(nome), Cliente.class);
    }

    @Override
    public Cliente buscarPorId(UUID id) {
        return MapperUtil.convertTo(clienteRepository.findById(id).orElse(null), Cliente.class);
    }

    @Override
    public List<Cliente> buscarPorId(List<UUID> uuidList) {
        return MapperUtil.mapList(clienteRepository.findAllById(uuidList), Cliente.class);
    }

    @Override
    public Cliente salvar(Cliente model) {
        ClienteEntity entity = MapperUtil.convertTo(model, ClienteEntity.class);
        return MapperUtil.convertTo(clienteRepository.save(entity), Cliente.class);
    }


    @Override
    public List<Cliente> salvar(List<Cliente> modelList) {
        List<ClienteEntity> entityList = MapperUtil.mapList(modelList, ClienteEntity.class);
        return MapperUtil.mapList(clienteRepository.saveAll(entityList), Cliente.class);
    }

    @Override
    public Cliente editar(Cliente model) {
        Cliente clienteSalvo = this.buscarPorId(model.getId());
        if (clienteSalvo != null) {
            model.setAtivo(clienteSalvo.isAtivo());
            model.setDataCriacao(clienteSalvo.getDataCriacao());
            clienteSalvo = salvar(model);
        }

        return clienteSalvo;
    }

    @Override
    public Cliente excluir(UUID id) {
        ClienteEntity entityEdited = clienteRepository.findById(id).orElseGet(null);
        if (entityEdited != null) {
            entityEdited.setAtivo(false);
            entityEdited = clienteRepository.save(entityEdited);
        }
        return MapperUtil.convertTo(entityEdited, Cliente.class);
    }
}
