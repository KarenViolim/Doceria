package com.app.Doceria.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.Conexao;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Controller
public class RelatorioController implements Serializable {

	@Autowired
	ServletContext context;
//	private Date dataInicial;
//	private Date dataFinal;

	@GetMapping("administrativo/relatorios/cliente")
	public ModelAndView relCliente() {
		ModelAndView mv = new ModelAndView("/administrativo/relatorios/cliente");
		return mv;
	}

	@GetMapping("/administrativo/rel/clientes-pdf")
	public void gerarRelatorioClientes(HttpServletResponse response) {
		try {
			InputStream stream = this.getClass()
					.getResourceAsStream("/listaClientes.jrxml");

			JasperReport report = JasperCompileManager.compileReport(stream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, null,
					Conexao.getConection());
			
			response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "attachement; filename=\"Relatório de Clientes.pdf\"");

		    final OutputStream outStream = response.getOutputStream();
		    JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// GETTERS AND SETTERS

//	public Date getDataInicial() {
//		return dataInicial;
//	}
//
//	public void setDataInicial(Date dataInicial) {
//		this.dataInicial = dataInicial;
//	}
//
//	public Date getDataFinal() {
//		return dataFinal;
//	}
//
//	public void setDataFinal(Date dataFinal) {
//		this.dataFinal = dataFinal;
//	}

}
