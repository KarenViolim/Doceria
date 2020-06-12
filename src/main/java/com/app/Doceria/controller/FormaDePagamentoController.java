package com.app.Doceria.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.FormaDePagamento;
import com.app.Doceria.repository.FormaPagamentoRepository;

@Controller
public class FormaDePagamentoController {
	@Autowired
	public FormaPagamentoRepository repository;
	
	@GetMapping("administrativo/formapagamentos/listar")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/formapagamentos/lista");
		List<FormaDePagamento> formadepagamento = repository.findAll();
		mv.addObject("formadepagamento", formadepagamento);
		return mv;
	}
	
	@GetMapping("administrativo/formapagamentos/cadastrar")
	public ModelAndView add(FormaDePagamento formadepagamento) {
		ModelAndView mv = new ModelAndView("/administrativo/formapagamentos/cadastro");
		mv.addObject("formadepagamento", formadepagamento);
		return mv;
	}
	@GetMapping("administrativo/formapagamentos/editarFormaPagamento/{id}")
	public ModelAndView editar(@PathVariable("id") Long id){
		Optional<FormaDePagamento> formadepagamento = repository.findById(id);
		FormaDePagamento forma = formadepagamento.get();
		return add(forma);
		
	}
	@GetMapping("administrativo/formapagamentos/removerFormaPagamento/{id}")
	public ModelAndView remover(@PathVariable("id") Long id){
		Optional<FormaDePagamento> formadepagamento = repository.findById(id);
		FormaDePagamento forma = formadepagamento.get();
		repository.delete(forma);
		return lista();
		
	}
	@PostMapping("administrativo/formapagamentos/salvarFormaPagamento")
	public ModelAndView salvar(@Valid FormaDePagamento formadepagamento, BindingResult result ) {
		if(result.hasErrors()) {
			
		}
		repository.saveAndFlush(formadepagamento);
		return lista();
	}
}
