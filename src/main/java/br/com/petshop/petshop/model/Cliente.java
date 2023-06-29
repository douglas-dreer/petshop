package br.com.petshop.petshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Pessoa{
    private UUID id;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private boolean isAtivo;
}
