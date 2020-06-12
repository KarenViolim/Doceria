package com.app.Doceria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;



@Controller
public class NegadoController {

	@GetMapping("/negado")
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView("/negadoAdministrador");
		return mv;
	}
	
	@GetMapping("/negadoCliente")
	public ModelAndView negado() {
		ModelAndView mv = new ModelAndView("/negadoCliente");
		return mv;
	}
}
