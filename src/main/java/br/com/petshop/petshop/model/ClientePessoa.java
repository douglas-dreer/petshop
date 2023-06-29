package br.com.petshop.petshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientePessoa {
    private UUID id;
    private Cliente cliente;
    private Pessoa pessoa;
}
