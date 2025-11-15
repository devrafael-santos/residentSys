package com.raffasdev.residentsys.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "moradores")
@Getter
@Setter
public class Morador {

    @Id
    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @NotEmpty
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Size(min = 11, max = 11)
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @OneToOne(mappedBy = "morador")
    private Casa casa;
}
