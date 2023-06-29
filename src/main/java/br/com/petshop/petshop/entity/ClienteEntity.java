package br.com.petshop.petshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
@Data
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, insertable = false)
    private UUID id;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_mofificacao")
    private LocalDateTime dataModificacao;

    @Column(name = "is_ativo", nullable = false)
    private boolean isAtivo = true;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.isAtivo = true;
    }

    @PostUpdate
    public void postUpdate() {
        this.dataModificacao = LocalDateTime.now();
    }


}
