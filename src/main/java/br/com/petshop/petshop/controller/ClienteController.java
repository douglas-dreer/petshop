package br.com.petshop.petshop.controller;

import br.com.petshop.petshop.model.Cliente;
import br.com.petshop.petshop.service.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final HttpServletRequest httpServletRequest;

    public ClienteController(ClienteService clienteService, HttpServletRequest httpServletRequest) {
        this.clienteService = clienteService;
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable(name = "uuid") UUID uuid) {
        return ResponseEntity.ok(clienteService.buscarPorId(uuid));
    }

    @GetMapping(path = "", params = "uuidList")
    public ResponseEntity<List<Cliente>> buscarPorIds(@PathVariable(name = "uuidList") List<UUID> uuidList) {
        return ResponseEntity.ok(clienteService.buscarPorId(uuidList));
    }

    @GetMapping(path = "/", params = "nome")
    public ResponseEntity<List<Cliente>> buscarPorNomeOrSobrenome(@RequestParam(name = "nome") String nome) {
        return ResponseEntity.ok(clienteService.listarPorNome(nome));
    }

    @PutMapping
    public ResponseEntity<?> salvar(@RequestBody Cliente model) throws URISyntaxException {
        Cliente clienteSalvo = clienteService.salvar(model);
        return ResponseEntity.created(new URI(String.format("%s/%s", httpServletRequest.getRequestURL(), clienteSalvo.getId()))).build();
    }

    @PutMapping(path = "/")
    public ResponseEntity<?> salvarTodos(@RequestBody List<Cliente> modelList) throws URISyntaxException {
        StringBuilder idList = new StringBuilder();

        for (Cliente cliente : clienteService.salvar(modelList)) {
            idList.append(String.format("%s,", cliente.getId()));
        }

        return ResponseEntity.created(new URI(String.format("%s?id=%s", httpServletRequest.getRequestURL(), idList.toString()))).build();
    }

    @PatchMapping(path = "{uuid}")
    public ResponseEntity<?> editar(@PathVariable(name = "uuid") UUID uuid, @RequestBody Cliente model) throws URISyntaxException {
        Cliente clienteSalvo = clienteService.editar(model);
        return ResponseEntity.created(new URI(String.format("%s/%s", httpServletRequest.getRequestURL(), clienteSalvo.getId()))).build();
    }

    @DeleteMapping(path = "{uuid}")
    public void excluir(@PathVariable("uuid") UUID uuid) {
        clienteService.excluir(uuid);
    }


}
