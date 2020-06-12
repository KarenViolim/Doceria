package com.app.Doceria.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.ItensCompra;
import com.app.Doceria.repository.ProdutoRepository;

@Controller
public class Index2Controller {

	@Autowired
	private ProdutoRepository repositoryProduto;
	
	private List<ItensCompra> ItensCompra = new ArrayList<ItensCompra>();
	
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("clientes/index");
		// System.out.println("Produtos: "+ repositoryProduto.findAll().size());
		mv.addObject("produtos", repositoryProduto.findAll());
		mv.addObject("listaItens", ItensCompra);
		return mv;
	}

	@GetMapping("/blog")
	public ModelAndView blog() {
		ModelAndView mv = new ModelAndView("clientes/blog");
		return mv;
	}

	@GetMapping("/contato")
	public ModelAndView contato() {
		ModelAndView mv = new ModelAndView("clientes/contato");
		return mv;
	}
}
