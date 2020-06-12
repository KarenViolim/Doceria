package com.app.Doceria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Doceria.service.EstadoReportService;

@RestController
@RequestMapping("administrativo/relatorioEstado")
public class RelatorioController {

	@Autowired
	private EstadoReportService estadoReportService;

	@GetMapping("/administrativo/relatorioEstado")
	public String empReport() {

		return estadoReportService.generateReport();
	}
}