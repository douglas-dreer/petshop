package br.com.petshop.petshop.controller;

import br.com.petshop.petshop.model.Cliente;
import br.com.petshop.petshop.model.ClientePessoa;
import br.com.petshop.petshop.model.Pessoa;
import br.com.petshop.petshop.service.ClientePessoaService;
import br.com.petshop.petshop.service.ClienteService;
import br.com.petshop.petshop.service.PessoaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {
    private final ClienteService clienteService;
    private final PessoaService pessoaService;
    private final ClientePessoaService clientePessoaService;
    private final HttpServletRequest httpServletRequest;

    public CadastroController(ClienteService clienteService, PessoaService pessoaService, ClientePessoaService clientePessoaService, HttpServletRequest httpServletRequest) {
        this.clienteService = clienteService;
        this.pessoaService = pessoaService;
        this.clientePessoaService = clientePessoaService;
        this.httpServletRequest = httpServletRequest;
    }

    @PostMapping(path = "/cliente")
    public ResponseEntity<ClientePessoa> salvar(@RequestBody Cliente model) throws URISyntaxException {
        Pessoa pessoaSalvo = pessoaService.salvar(model);
        Cliente clienteSalvo = clienteService.salvar(model);

        ClientePessoa clientePessoa = ClientePessoa.builder()
                .cliente(clienteSalvo)
                .pessoa(pessoaSalvo)
                .build();

        clientePessoaService.salvar(clientePessoa);
        String urlRetorno = String.format("%s/%s", httpServletRequest.getRequestURL(), clienteSalvo.getId());
        URI location = new URI(urlRetorno);
        return ResponseEntity.created(location).build();
    }


}
