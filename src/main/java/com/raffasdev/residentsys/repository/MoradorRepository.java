package com.raffasdev.residentsys.repository;

import com.raffasdev.residentsys.model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoradorRepository extends JpaRepository<Morador, String> {
    boolean existsByEmail(String email);
}
