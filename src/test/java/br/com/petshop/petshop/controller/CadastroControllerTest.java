package br.com.petshop.petshop.controller;

import br.com.petshop.petshop.model.Cliente;
import br.com.petshop.petshop.model.ClientePessoa;
import br.com.petshop.petshop.model.Pessoa;
import br.com.petshop.petshop.service.ClientePessoaService;
import br.com.petshop.petshop.service.ClienteService;
import br.com.petshop.petshop.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.petshop.petshop.util.MapperUtil.toJSON;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CadastroControllerTest {
    private final static String ENDPOINT = "/cadastro";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private PessoaService pessoaService;

    @MockBean
    private ClientePessoaService clientePessoaService;


    private final Cliente cliente = new Cliente();

    private final Pessoa pessoa = new Pessoa();

    private ClientePessoa clientePessoa = new ClientePessoa();

    @BeforeEach
    void setup() {
        this.cliente.setId(UUID.randomUUID());
        this.cliente.setNome("Douglas");
        this.cliente.setSobrenome("Dreer");
        this.cliente.setApelido("Söejin");
        this.cliente.setEmail("douglasdreer@outlook.com.br");
        this.cliente.setDataCriacao(LocalDateTime.now());
        this.cliente.setDataModificacao(LocalDateTime.now());
        this.cliente.setAtivo(true);

        this.pessoa.setNome("Douglas");
        this.pessoa.setSobrenome("Dreer");
        this.pessoa.setApelido("Söejin");
        this.pessoa.setEmail("douglasdreer@outlook.com.br");
        this.pessoa.setDataCriacao(LocalDateTime.now());
        this.pessoa.setDataModificacao(LocalDateTime.now());
        this.pessoa.setAtivo(true);

        this.clientePessoa = ClientePessoa.builder()
                .cliente(this.cliente)
                .pessoa(this.pessoa)
                .build();
    }

    @Test
    void mustReturnSuccessWithResults_WhenSalvar() throws Exception {
        when(pessoaService.salvar((Pessoa) any())).thenReturn(this.pessoa);
        when(clienteService.salvar((Cliente) any())).thenReturn(this.cliente);

        String clientJSON = toJSON(this.cliente);

        final String URI = ENDPOINT.concat("/cliente");

        MockHttpServletRequestBuilder postMethod = post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJSON);

        mockMvc.perform(postMethod)
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString(this.cliente.getId().toString())));

    }



}
