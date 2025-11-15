package com.raffasdev.residentsys.repository;

import com.raffasdev.residentsys.model.Casa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CasaRepository extends JpaRepository<Casa, UUID> {
    boolean existsByEnderecoAndNumCasa(String endereco, String numCasa);
    Optional<Casa> findByMoradorCpf(String cpf);
}
