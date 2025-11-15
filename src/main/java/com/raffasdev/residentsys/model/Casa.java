package com.raffasdev.residentsys.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "casas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Casa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "casa_id", unique = true, nullable = false)
    private UUID casaId;

    @NotEmpty
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @NotEmpty
    @Column(name = "num_casa", nullable = false)
    private String numCasa;

    @OneToMany(mappedBy = "casa", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Cobranca> cobrancas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usuario_login", nullable = false)
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "morador_id", nullable = false)
    private Morador morador;
}
