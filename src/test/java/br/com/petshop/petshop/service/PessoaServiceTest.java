package br.com.petshop.petshop.service;

import br.com.petshop.petshop.entity.PessoaEntity;
import br.com.petshop.petshop.model.Pessoa;
import br.com.petshop.petshop.repository.PessoaRepository;
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
class PessoaServiceTest {
    @Mock
    private PessoaRepository repository;

    @InjectMocks
    private PessoaServiceImpl service;

    private final UUID id = UUID.randomUUID();
    private List<PessoaEntity> pessoaEntityList = new ArrayList<>();
    private final PessoaEntity pessoa = new PessoaEntity();

    @BeforeEach
    void setup() {
        this.pessoa.setNome("Douglas");
        this.pessoa.setSobreNome("Dreer");
        this.pessoa.setApelido("Soejin");
        this.pessoa.setEmail("douglasdreer@outlook.com.br");
        this.pessoa.setDataCriacao(LocalDateTime.now());
        this.pessoa.setDataModificacao(LocalDateTime.now());
        this.pessoa.setAtivo(true);

        this.pessoaEntityList = Collections.singletonList(this.pessoa);
    }

    @Test
    void mustReturnSuccessWithResult_WhenListar() {
        when(repository.findAll()).thenReturn(this.pessoaEntityList);

        List<Pessoa> resultadoList = service.listar();

        assertFalse(resultadoList.isEmpty());
        assertEquals(1, resultadoList.size());
    }

    @Test
    void mustReturnSuccessWithResult_WhenListarPorNome() {
        final String TERMO = "dREER";
        when(repository.findClienteEntitiesByNomeContainsIgnoreCase(anyString())).thenReturn(this.pessoaEntityList);

        List<Pessoa> resultadoList = service.listarPorNome(TERMO);

        assertFalse(resultadoList.isEmpty());
        assertEquals(1, resultadoList.size());
    }

    @Test
    void mustReturnSuccessWithResult_WhenListarPorId() {
        final UUID termo = this.pessoa.getId();

        when(repository.findById(any())).thenReturn(Optional.of(this.pessoa));

        Pessoa resultado = service.buscarPorId(termo);

        assertNotNull(resultado);
        assertEquals(termo, resultado.getId());
    }

    @Test
    void mustReturnSuccessWithResults_WhenListarPorId() {
        this.pessoa.setId(id);
        final List<UUID> termoList = Collections.singletonList(id);

        when(repository.findAllById(anyList())).thenReturn(this.pessoaEntityList);

        List<Pessoa> resultadoList = service.buscarPorId(termoList);

        assertFalse(resultadoList.isEmpty());
        assertEquals(1, resultadoList.size());
        assertNotNull(resultadoList.get(0));
        assertEquals(termoList.get(0), resultadoList.get(0).getId());
    }

    @Test
    void mustReturnSuccessWithResult_WhenListarPorStatus() {
        final boolean termo = true;

        when(repository.findAllByIsAtivo(anyBoolean())).thenReturn(this.pessoaEntityList);

        List<Pessoa> resultadoList = service.listarPorStatus(termo);

        assertFalse(resultadoList.isEmpty());
        assertEquals(1, resultadoList.size());
        assertNotNull(resultadoList.get(0));
        assertEquals(termo, resultadoList.get(0).isAtivo());
    }

    @Test
    void mustReturnSuccessWithResult_WhenSalvar() {
        when(repository.save(any())).thenReturn(this.pessoa);

        Pessoa dados = convertTo(this.pessoa, Pessoa.class);
        Pessoa resultado = service.salvar(dados);

        assertNotNull(resultado);
        assertEquals(dados, resultado);
    }

    @Test
    void mustReturnSuccessWithResults_WhenSalvar() {
        when(repository.saveAll(anyList())).thenReturn(this.pessoaEntityList);

        List<Pessoa> dadosList = mapList(this.pessoaEntityList, Pessoa.class);
        List<Pessoa> resultadoList = service.salvar(dadosList);

        assertFalse(resultadoList.isEmpty());
        assertEquals(1, resultadoList.size());
        assertEquals(dadosList, resultadoList);
    }

    @Test
    void mustReturnSuccessWithResult_WhenEditar() {
        when(repository.findById(any())).thenReturn(Optional.of(this.pessoa));
        when(repository.save(any())).thenReturn(this.pessoa);

        Pessoa dados = convertTo(this.pessoa, Pessoa.class);
        Pessoa resultado = service.editar(dados);

        assertNotNull(resultado);
        assertEquals(dados, resultado);
    }

    @Test
    void mustReturnSuccessWithoutResult_WhenEditar() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(this.pessoa);

        Pessoa dados = convertTo(this.pessoa, Pessoa.class);
        Pessoa resultado = service.editar(dados);

        assertNull(resultado);
    }

    @Test
    void mustReturnSuccessWithResult_WhenExcluir() {
        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(any())).thenReturn(this.pessoa);

        service.excluir(id);

    }

    @Test
    void mustReturnSuccessWithoutResult_WhenExcluir() {
        when(repository.existsById(id)).thenReturn(false);
        when(repository.save(any())).thenReturn(this.pessoa);

        service.excluir(id);

    }
}
