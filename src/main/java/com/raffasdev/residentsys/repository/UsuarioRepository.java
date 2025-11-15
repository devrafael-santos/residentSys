package com.raffasdev.residentsys.repository;

import com.raffasdev.residentsys.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
