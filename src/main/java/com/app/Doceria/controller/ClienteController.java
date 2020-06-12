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

import com.app.Doceria.model.Cliente;
import com.app.Doceria.repository.CidadeRepository;
import com.app.Doceria.repository.ClienteRepository;

@Controller
public class ClienteController {
	@Autowired
	private ClienteRepository repositoryCliente;
	
	@Autowired
	public CidadeRepository repositoryCidade;
	

	@GetMapping("administrativo/clientes/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/clientes/lista");
		mv.addObject("cliente", repositoryCliente.findAll());
		return mv;
	}
	
	@GetMapping("administrativo/clientes/editarCliente/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
		Optional<Cliente> op = repositoryCliente.findById(id);
		Cliente cli = op.get();
		return add(cli);
	}
	
	@GetMapping("administrativo/clientes/removerCliente/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
		Optional<Cliente> op = repositoryCliente.findById(id);
		Cliente cli = op.get();
		repositoryCliente.delete(cli);
		return listar();
	}
	
	@PostMapping("clientes/salvarCliente")
	public ModelAndView salvarCliente(Cliente cliente) {
		repositoryCliente.save(cliente);
		return listar();
	}
	
	@GetMapping("clientes/cadastrar")
	public ModelAndView add(Cliente cliente) {
		ModelAndView mv = new ModelAndView("/clientes/clienteCadastro");
		mv.addObject("cliente", cliente);
		mv.addObject("cidades", repositoryCidade.findAll());
		return mv;
	}
	
	@GetMapping("clientes/editarCliente/{id}")
	public ModelAndView editarCliente(@PathVariable("id") long id) {
		Optional<Cliente> op = repositoryCliente.findById(id);
		return add(op.get());
	}
	
	@PostMapping("clientes/salvar")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result) {
		
		if(result.hasErrors()) {
			return add(cliente);
		}
		cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
		repositoryCliente.save(cliente);
		
		return add(new Cliente());
	}
}
