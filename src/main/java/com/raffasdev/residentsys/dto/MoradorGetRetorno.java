package com.raffasdev.residentsys.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MoradorGetRetorno(String cpf, String nome, String email, String telefone, UUID casaId) {
}
