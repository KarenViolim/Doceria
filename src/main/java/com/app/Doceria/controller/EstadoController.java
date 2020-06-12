package com.app.Doceria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.Estado;
import com.app.Doceria.repository.EstadoRepository;

@Controller
public class EstadoController {
	@Autowired
	public EstadoRepository repositoryEstado;
	
	@GetMapping("/administrativo/estados/cadastrar")
	public ModelAndView add(Estado estado) {
		ModelAndView mv = new ModelAndView("/administrativo/estados/cadastro");
		mv.addObject("estado", estado);
		return mv;
	}
	
	@GetMapping("/administrativo/estados/listar")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/estados/lista");
		List<Estado> estado = repositoryEstado.findAll();
		mv.addObject("estado", estado);
		return mv;
	}
	
	@GetMapping("/administrativo/estados/editarEstado/{id}")
	public ModelAndView editar(@PathVariable("id") Long id){
		Optional<Estado> estado = repositoryEstado.findById(id);
		Estado e = estado.get();
		return add(e);
		
	}
	@GetMapping("/administrativo/estados/removerEstado/{id}")
	public ModelAndView remover(@PathVariable("id") Long id){
		Optional<Estado> estado = repositoryEstado.findById(id);
		Estado e = estado.get();
		repositoryEstado.delete(e);
		return lista();
		
	}
	
	@RequestMapping(value = "administrativo/estados/salvarEstado", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE ,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Estado salvar(@RequestBody Estado estado, BindingResult result) {
		repositoryEstado.saveAndFlush(estado);
		return estado;
	   }

}
