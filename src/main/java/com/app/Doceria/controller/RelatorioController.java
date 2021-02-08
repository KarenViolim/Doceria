package com.app.Doceria.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.Conexao;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Controller
public class RelatorioController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	ServletContext context;
	private Date dtInicio;
	private Date dtFim;

	//CHAMADA PARA A PÁGINA QUE TEM O BOTÃO PARA GERAR O RELATÓRIO DE CLIENTES
	@GetMapping("administrativo/relatorios/cliente")
	public ModelAndView relCliente() {
		ModelAndView mv = new ModelAndView("/administrativo/relatorios/cliente");
		return mv;
	}

	//CHAMADA PARA A PÁGINA QUE TEM O BOTÃO PARA GERAR O RELATÓRIO DE VENDAS POR PERÍODO
	@GetMapping("administrativo/relatorios/vendas")
	public ModelAndView relVendas() {
		ModelAndView mv = new ModelAndView("/administrativo/relatorios/vendas");
		return mv;
	}

	//MÉTODO QUE GERA O RELATÓRIO DE CLIENTES
	@GetMapping("/administrativo/rel/clientes-pdf")
	public void gerarRelatorioClientes(HttpServletResponse response) {
		try {
			InputStream stream = this.getClass().getResourceAsStream("/listaClientes.jrxml");

			JasperReport report = JasperCompileManager.compileReport(stream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, Conexao.getConection());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=\"Relatório de Clientes.pdf\"");

			final OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//MÉTODO QUE GERA O RELATÓRIO DE VENDAS POR PERÍODO
	@RequestMapping(value = "/administrativo/rel/vendas-pdf", method = RequestMethod.GET)
	@ResponseBody
	public void gerarRelatorioVendas(HttpServletResponse response) {
		try {
			HashMap<String, Object> p = new HashMap<>();
			p.put("dtInicio", dtInicio);
			p.put("dtFim", dtFim);

			InputStream stream = this.getClass().getResourceAsStream("/vendas.jrxml");

			JasperReport report = JasperCompileManager.compileReport(stream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, p, Conexao.getConection());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=\"Relatório de Vendas.pdf\"");

			final OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// GETTERS AND SETTERS
	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicial(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

}
