package com.raffasdev.residentsys.repository;

import com.raffasdev.residentsys.model.Cobranca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CobrancaRepository extends JpaRepository<Cobranca, UUID> {
    List<Cobranca> findCobrancasByCasa_CasaId(UUID casaId);
}
