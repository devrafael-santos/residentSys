package com.raffasdev.residentsys.exception;

public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException() {
        super("Credenciais inválidas");
    }
}
