package br.com.petshop.petshop.service;

import br.com.petshop.petshop.entity.PessoaEntity;
import br.com.petshop.petshop.model.Pessoa;
import br.com.petshop.petshop.repository.PessoaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static br.com.petshop.petshop.util.MapperUtil.convertTo;
import static br.com.petshop.petshop.util.MapperUtil.mapList;

@Service
@Log4j2
public class PessoaServiceImpl implements PessoaService {
    private final PessoaRepository repository;

    public PessoaServiceImpl(PessoaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Pessoa> listar() {
        List<PessoaEntity> entityList = repository.findAll();
        return mapList(entityList, Pessoa.class);
    }

    @Override
    public Pessoa buscarPorId(UUID id) {
        PessoaEntity pessoaEntity = repository.findById(id).orElse(null);
        return convertTo(pessoaEntity, Pessoa.class);
    }

    @Override
    public List<Pessoa> buscarPorId(List<UUID> uuidList) {
        List<PessoaEntity> resultados = repository.findAllById(uuidList);
        return mapList(resultados, Pessoa.class);
    }

    @Override
    public List<Pessoa> listarPorNome(String nome) {
        List<PessoaEntity> pessoaEntityList = repository.findClienteEntitiesByNomeContainsIgnoreCase(nome);
        return mapList(pessoaEntityList, Pessoa.class);
    }

    @Override
    public List<Pessoa> salvar(List<Pessoa> modelList) {
        return modelList.stream()
                .map(this::salvar)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Pessoa salvar(Pessoa model) {
        PessoaEntity entity = convertTo(model, PessoaEntity.class);
        repository.save(entity);
        return convertTo(entity, Pessoa.class);
    }

    @Override
    public Pessoa editar(Pessoa model) {
        return this.buscarPorId(model.getId()) == null ?
                null :
                this.salvar(model);
    }

    @Override
    public void excluir(UUID id) {
        boolean pessoaExiste = repository.existsById(id);
        if (!pessoaExiste) {
            log.error(String.format("Pessoa com o id %s não existe no banco de dados", id));
        }
        repository.deleteById(id);
    }

    @Override
    public List<Pessoa> listarPorStatus(boolean isAtivo) {
        List<PessoaEntity> pessoaEntityList = repository.findAllByIsAtivo(isAtivo);
        return mapList(pessoaEntityList, Pessoa.class);
    }


}
