package br.com.petshop.petshop.service;

import br.com.petshop.petshop.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.yml")
class ClienteServiceIntergrationTest {
    @Autowired
    private ClienteService service;

    private Cliente cliente = new Cliente();
    private List<Cliente> clienteList = Arrays.asList(
            new Cliente(), new Cliente(),
            new Cliente(), new Cliente()
    );

    @Test
    void returnSuccess_WhenListar() {
        service.salvar(clienteList);

        List<Cliente> resultados = service.listar();

        assertFalse(resultados.isEmpty());
    }

    @Test
    void returnSuccess_WhenBuscaPorId() {
        UUID id = service.salvar(cliente).getId();

        Cliente resultado = service.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    void returnSuccessWithoutResults_WhenBuscarPorId() {
        service.salvar(cliente);
        UUID id = UUID.randomUUID();

        Cliente resultado = service.buscarPorId(id);

        assertNull(resultado);
    }

    @Test
    void returnSuccessWithList_WhenBuscaPorId() {
        List<UUID> idList = service.salvar(clienteList).stream().map(Cliente::getId).toList();

        List<Cliente> resultados = service.buscarPorId(idList);

        assertFalse(resultados.isEmpty());

        Set<UUID> resultadoIds = resultados.stream()
                .map(Cliente::getId)
                .collect(Collectors.toSet());

        assertTrue(resultadoIds.containsAll(idList));
    }

    @Test
    void returnSuccess_WhenBuscarPorStatus() {
        final boolean status = false;
        List<Cliente> clienteSalvoList = service.salvar(clienteList);

        List<Cliente> clienteInativoList = Arrays.asList(clienteSalvoList.get(0), clienteSalvoList.get(1));
        clienteInativoList.forEach(cliente -> cliente.setAtivo(false));
        service.salvar(clienteInativoList);

        List<Cliente> resultados = service.listarPorStatus(status);

        assertFalse(resultados.isEmpty());
        assertTrue(resultados.stream().allMatch(cliente -> !cliente.isAtivo()));
    }

    @Test
    void returnSuccess_WhenSalvar() {
        Cliente resultado = service.salvar(cliente);
        assertNotNull(resultado);
    }

    @Test
    void returnSuccessWithList_WhenSalvar() {
        List<Cliente> resultados = service.salvar(clienteList);

        assertFalse(resultados.isEmpty());
    }

    @Test
    void returnSuccess_WhenEditar() {
        Cliente clienteSalvo = service.salvar(cliente);
        clienteSalvo.setAtivo(false);

        service.editar(clienteSalvo);

        Cliente resultado = service.buscarPorId(clienteSalvo.getId());

        assertNotNull(resultado);
        assertFalse(resultado.isAtivo());
        assertNotNull(resultado.getDataModificacao());
    }

    @Test
    void eturnSuccessWithNotFound_WhenEditar() {
        Cliente resultado = service.buscarPorId(UUID.randomUUID());

        assertNull(resultado);
    }

    @Test
    void returnSuccess_WhenExcluir() {
        Cliente clienteSalvo = service.salvar(cliente);

        Cliente resultado = service.excluir(clienteSalvo.getId());

        assertNotNull(resultado);
        assertFalse(resultado.isAtivo());
        assertNotNull(resultado.getDataModificacao());
    }

    @Test
    void returnSuccessWithNotFound_WhenExcluir() {
        Cliente resultado = service.buscarPorId(UUID.randomUUID());

        assertNull(resultado);
    }
}
