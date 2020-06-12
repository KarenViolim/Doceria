package com.app.Doceria.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.Funcionario;
import com.app.Doceria.repository.CidadeRepository;
import com.app.Doceria.repository.FuncionarioRepository;
import com.app.Doceria.repository.PapeisRepository;

@Controller
public class FuncionarioController {
	@Autowired
	private FuncionarioRepository repositoryFuncionario;
	
	@Autowired
	public CidadeRepository repositoryCidade;
	
	@Autowired
	public PapeisRepository repositoryPapeis;
	
	@GetMapping("/administrativo/funcionarios/cadastrar")
	public ModelAndView add(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/administrativo/funcionarios/cadastro");
		mv.addObject("funcionario", funcionario);
		mv.addObject("cidades", repositoryCidade.findAll());
		mv.addObject("papeis", repositoryPapeis.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/funcionarios/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/funcionarios/lista");
		mv.addObject("funcionario", repositoryFuncionario.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/funcionarios/editarFuncionario/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
		Optional<Funcionario> op = repositoryFuncionario.findById(id);
		Funcionario func = op.get();
		return add(func);
	}
	
	@GetMapping("/administrativo/funcionarios/removerFuncionario/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
		Optional<Funcionario> op = repositoryFuncionario.findById(id);
		Funcionario func = op.get();
		repositoryFuncionario.delete(func);
		return listar();
	}
	
	@PostMapping("/administrativo/funcionarios/salvarFuncionario")

	public ModelAndView salvar(@Valid Funcionario funcionario, BindingResult result) {
		
		if(result.hasErrors()) {
			return add(funcionario);
		}
		funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));
		repositoryFuncionario.save(funcionario);
		
		return add(new Funcionario());
		
	}
}

