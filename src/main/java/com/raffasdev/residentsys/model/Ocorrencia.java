package com.raffasdev.residentsys.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "ocorrencias")
@Getter
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ocorrencia_id", unique = true, nullable = false)
    private UUID ocorrenciaId;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "data_abertura", nullable = false)
    private LocalDate dataAbertura;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "usuario_login", nullable = false)
    private Usuario usuario;
}
