package com.app.Doceria.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.ItensEntrada;
import com.app.Doceria.repository.ItensEntradaRepository;
import com.app.Doceria.repository.ProdutoRepository;

@Controller
public class ItensEntradaController {
	
	@Autowired
	private ItensEntradaRepository repositoryItens;
	
	@Autowired
	private ProdutoRepository repositoryProduto;

	
	@GetMapping("administrativo/itensentrada/cadastrar")
	public ModelAndView add(ItensEntrada itens) {
		ModelAndView mv = new ModelAndView("/administrativo/itensentrada/cadastro");
		mv.addObject("itens", itens);
		mv.addObject("produtos", repositoryProduto.findAll());
		return mv;
	}
	
	@GetMapping("administrativo/itensentrada/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/itensentrada/lista");
		mv.addObject("itens", repositoryItens.findAll());
		return mv;
	}
	
	
	@GetMapping("administrativo/itensentrada/editarItens/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
		Optional<ItensEntrada> op = repositoryItens.findById(id);
		ItensEntrada p = op.get();
		return add(p);
	}
	
	@GetMapping("administrativo/itensentrada/removerItens/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
		Optional<ItensEntrada> op = repositoryItens.findById(id);
		ItensEntrada p = op.get();
		repositoryItens.delete(p);
		return listar();
	}
	
	@PostMapping("administrativo/itensentrada/salvarItens")
	public ModelAndView salvar(ItensEntrada itens) {
		repositoryItens.save(itens);
		return listar();
	}
	

}
