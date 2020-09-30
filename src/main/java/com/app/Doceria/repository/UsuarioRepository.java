package com.app.Doceria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Doceria.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
