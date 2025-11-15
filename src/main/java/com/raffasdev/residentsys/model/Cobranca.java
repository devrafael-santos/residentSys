package com.raffasdev.residentsys.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cobrancas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cobranca {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cobranca_id", unique = true, nullable = false)
    private UUID cobrancaId;

    @Column(name = "valor", nullable = false)
    private float valor;

    @Column(name = "data_vencimento", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "status", nullable = false)
    private String status = "pendente";

    @ManyToOne
    @JoinColumn(name = "casa_id", nullable = false)
    private Casa casa;

}
