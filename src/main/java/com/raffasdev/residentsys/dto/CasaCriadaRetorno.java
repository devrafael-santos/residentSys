package com.raffasdev.residentsys.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CasaCriadaRetorno(UUID casaId, String endereco, String numCasa, String moradorCpf, String usuarioLogin) {
}
