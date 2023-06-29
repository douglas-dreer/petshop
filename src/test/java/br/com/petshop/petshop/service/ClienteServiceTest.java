package br.com.petshop.petshop.service;

import br.com.petshop.petshop.entity.ClienteEntity;
import br.com.petshop.petshop.model.Cliente;
import br.com.petshop.petshop.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static br.com.petshop.petshop.util.MapperUtil.convertTo;
import static br.com.petshop.petshop.util.MapperUtil.mapList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteServiceImpl service;

    private final UUID id = UUID.randomUUID();
    private List<ClienteEntity> clienteList = new ArrayList<>();
    private final ClienteEntity cliente = new ClienteEntity();

    @BeforeEach
    void setup() {

        this.cliente.setDataCriacao(LocalDateTime.now());
        this.cliente.setDataModificacao(LocalDateTime.now());
        this.cliente.setAtivo(true);

        this.clienteList = Collections.singletonList(this.cliente);
    }

    @Test
    void mustReturnSuccessWithResult_WhenListar() {
        when(repository.findAll()).thenReturn(this.clienteList);

        List<Cliente> resultadoList = service.listar();

        assertFalse(resultadoList.isEmpty());
        assertEquals(1, resultadoList.size());
    }

    @Test
    void mustReturnSuccessWithResult_WhenListarPorId() {
        final UUID termo = this.cliente.getId();

        when(repository.findById(any())).thenReturn(Optional.of(this.cliente));

        Cliente resultado = service.buscarPorId(termo);

        assertNotNull(resultado);
        assertEquals(termo, resultado.getId());
    }

    @Test
    void mustReturnSuccessWithResults_WhenListarPorId() {
        this.cliente.setId(id);
        final List<UUID> termoList = Collections.singletonList(id);

        when(repository.findAllById(anyList())).thenReturn(this.clienteList);

        List<Cliente> resultadoList = service.buscarPorId(termoList);

        assertFalse(resultadoList.isEmpty());
        assertEquals(1, resultadoList.size());
        assertNotNull(resultadoList.get(0));
        assertEquals(termoList.get(0), resultadoList.get(0).getId());
    }

    @Test
    void mustReturnSuccessWithResult_WhenListarPorStatus() {
        final boolean termo = true;

        when(repository.findAllByIsAtivo(anyBoolean())).thenReturn(this.clienteList);

        List<Cliente> resultadoList = service.listarPorStatus(termo);

        assertFalse(resultadoList.isEmpty());
        assertEquals(1, resultadoList.size());
        assertNotNull(resultadoList.get(0));
        assertEquals(termo, resultadoList.get(0).isAtivo());
    }

    @Test
    void mustReturnSuccessWithResult_WhenSalvar() {
        when(repository.save(any())).thenReturn(this.cliente);

        Cliente dados = convertTo(this.cliente, Cliente.class);
        Cliente resultado = service.salvar(dados);

        assertNotNull(resultado);
        assertEquals(dados, resultado);
    }

    @Test
    void mustReturnSuccessWithResults_WhenSalvar() {
        when(repository.saveAll(anyList())).thenReturn(this.clienteList);

        List<Cliente> dadosList = mapList(this.clienteList, Cliente.class);
        List<Cliente> resultadoList = service.salvar(dadosList);

        assertFalse(resultadoList.isEmpty());
        assertEquals(1, resultadoList.size());
        assertEquals(dadosList, resultadoList);
    }

    @Test
    void mustReturnSuccessWithResult_WhenEditar() {
        when(repository.findById(any())).thenReturn(Optional.of(this.cliente));
        when(repository.save(any())).thenReturn(this.cliente);

        Cliente dados = convertTo(this.cliente, Cliente.class);
        Cliente resultado = service.editar(dados);

        assertNotNull(resultado);
        assertNotNull(resultado.getDataModificacao());
    }

    @Test
    void mustReturnSuccessWithNotFound_WhenEditar() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Cliente dados = convertTo(this.cliente, Cliente.class);
        Cliente resultado = service.editar(dados);

        assertNull(resultado);
    }

    @Test
    void mustReturnSuccessWithResult_WhenExcluir() {
        when(repository.findById(any())).thenReturn(Optional.of(this.cliente));
        when(repository.save(any())).thenReturn(this.cliente);

        Cliente dados = convertTo(this.cliente, Cliente.class);
        Cliente resultado = service.excluir(id);

        assertNotNull(resultado);
    }
}
