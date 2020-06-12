package com.app.Doceria.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.PermissoesFuncionario;
import com.app.Doceria.repository.FuncionarioRepository;
import com.app.Doceria.repository.PapeisRepository;
import com.app.Doceria.repository.PermissoesFuncionarioRepository;

@Controller
public class PermissoesController {
	
	@Autowired
	private PermissoesFuncionarioRepository repositoryPermissoes;
	
	@Autowired
	private PapeisRepository repositoryPapeis;
	
	@Autowired
	public FuncionarioRepository repositoryFuncionario;
	
	@GetMapping("administrativo/permissoes/cadastrar")
	public ModelAndView add(PermissoesFuncionario permissoes) {
		ModelAndView mv = new ModelAndView("/administrativo/permissoes/cadastro");
		mv.addObject("permissoes", permissoes);
		mv.addObject("funcionarios", repositoryFuncionario.findAll());
		mv.addObject("papeis", repositoryPapeis.findAll());
		return mv;
	}
	
	@GetMapping("administrativo/permissoes/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/permissoes/lista");
		mv.addObject("permissoes", repositoryPermissoes.findAll());
		return mv;
	}
	
	
	@GetMapping("administrativo/permissoes/editarPermissao/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
		Optional<PermissoesFuncionario> op = repositoryPermissoes.findById(id);
		PermissoesFuncionario p = op.get();
		return add(p);
	}
	
	@GetMapping("administrativo/permissoes/removerPermissao/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
		Optional<PermissoesFuncionario> op = repositoryPermissoes.findById(id);
		PermissoesFuncionario p = op.get();
		repositoryPermissoes.delete(p);
		return listar();
	}
	
	@PostMapping("administrativo/permissoes/salvarPermissao")
	public ModelAndView salvar(PermissoesFuncionario permissoes) {
		repositoryPermissoes.save(permissoes);
		return listar();
	}
	

}
