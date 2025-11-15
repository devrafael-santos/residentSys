package com.raffasdev.residentsys.exception;

public class CasaExisteException extends RuntimeException {
    public CasaExisteException(String endereco) {
        super("Casa já existe com o endereco completo: " + endereco);
    }
}
