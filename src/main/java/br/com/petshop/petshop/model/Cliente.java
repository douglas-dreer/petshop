package br.com.petshop.petshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private UUID id;
    private String nome;
    private String sobrenome;
    private String apelido;
    private String email;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private boolean isAtivo;
}
