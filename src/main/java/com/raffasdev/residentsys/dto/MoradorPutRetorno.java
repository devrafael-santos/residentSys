package com.raffasdev.residentsys.dto;

import lombok.Builder;

@Builder
public record MoradorPutRetorno(String nome, String email, String telefone) {
}
