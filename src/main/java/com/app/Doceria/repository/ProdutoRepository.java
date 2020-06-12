package com.app.Doceria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.Doceria.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@Query("select p from Produto p where p.id = ?1")
	List<Produto> buscarProduto(long id);
}
