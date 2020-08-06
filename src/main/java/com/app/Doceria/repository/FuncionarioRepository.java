package com.app.Doceria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.Doceria.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
	
	@Query ("from Funcionario where email =?1")
	public List<Funcionario> buscarFuncionarioEmail(String email);
	
}
