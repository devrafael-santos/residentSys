package com.raffasdev.residentsys.dto;

import com.raffasdev.residentsys.model.Cobranca;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CasaGetRetorno(UUID casaId, String endereco, String numCasa,
                             List<Cobranca> cobrancas, String usuarioLogin, String moradorCpf) {
}
