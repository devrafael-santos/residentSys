package com.raffasdev.residentsys.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
public class Usuario {

    @Id
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @NotEmpty
    @Column(name = "senhaHash", nullable = false)
    @JsonProperty("senha")
    private String senhaHash;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ocorrencia> ocorrencias = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Casa> casas = new ArrayList<>();

    public Usuario(String login, String senhaHash) {
        this.login = login;
        this.senhaHash = senhaHash;
    }

    public Usuario(String login, String senhaHash, List<Ocorrencia> ocorrencias, List<Casa> casas) {
        this.login = login;
        this.senhaHash = senhaHash;
        this.ocorrencias = ocorrencias;
        this.casas = casas;
    }

    public Usuario() {

    }
}
