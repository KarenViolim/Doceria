package com.app.Doceria.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.CupomDesconto;
import com.app.Doceria.repository.CupomDescontoRepository;
import com.app.Doceria.repository.FuncionarioRepository;

@Controller
public class CupomDescontoController {
	
	@Autowired
	private CupomDescontoRepository repositoryCupom;
	
	@Autowired
	public FuncionarioRepository repositoryFuncionario;
	
	@GetMapping("administrativo/cupons/cadastrar")
	public ModelAndView add(CupomDesconto cupomdesconto) {
		ModelAndView mv = new ModelAndView("/administrativo/cupons/cadastro");
		mv.addObject("cupom", cupomdesconto);
		mv.addObject("funcionarios", repositoryFuncionario.findAll());
		return mv;
	}
	
	@GetMapping("administrativo/cupons/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/cupons/lista");
		mv.addObject("cupons", repositoryCupom.findAll());
		return mv;
	}
	
	
	@GetMapping("administrativo/cupons/editarCupom/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
		Optional<CupomDesconto> op = repositoryCupom.findById(id);
		CupomDesconto cup = op.get();
		return add(cup);
	}
	
	@GetMapping("administrativo/cupons/removerCupom/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
		Optional<CupomDesconto> op = repositoryCupom.findById(id);
		CupomDesconto cup = op.get();
		repositoryCupom.delete(cup);
		return listar();
	}
	
	@PostMapping("administrativo/cupons/salvarCupom")
	public ModelAndView salvar(CupomDesconto cupomdesconto) {
		repositoryCupom.save(cupomdesconto);
		return listar();
	}
	

}
