package com.raffasdev.residentsys.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CobrancaGetRetorno(UUID cobrancaId, float valor, LocalDate dataVencimento, LocalDate dataPagamento,
                                 String status, UUID casa_id) {
}
