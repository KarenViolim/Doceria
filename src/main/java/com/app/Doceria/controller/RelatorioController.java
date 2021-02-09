package com.app.Doceria.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.Conexao;
import com.app.Doceria.model.DataContainer;
import com.app.Doceria.model.Filtros;

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

	// CHAMADA PARA A PÁGINA QUE TEM O BOTÃO PARA GERAR O RELATÓRIO DE CLIENTES
	@GetMapping("administrativo/relatorios/cliente")
	public ModelAndView relCliente() {
		ModelAndView mv = new ModelAndView("/administrativo/relatorios/cliente");
		return mv;
	}

	// MÉTODO QUE GERA O RELATÓRIO DE CLIENTES
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
	
	// CHAMADA PARA A PÁGINA QUE TEM O BOTÃO PARA GERAR O RELATÓRIO DE VENDAS POR
	// PERÍODO
	@GetMapping("administrativo/relatorios/vendas")
	public String relVendas(Model model, final DataContainer dataContainer) {
		if(dataContainer.getDateTime()==null) {
			dataContainer.setDateTime(LocalDateTime.now());
		}
		model.addAttribute("filtro", new Filtros());
		return "/administrativo/relatorios/vendas";
	}


	// MÉTODO QUE GERA O RELATÓRIO DE VENDAS POR PERÍODO
	@PostMapping(value = "/administrativo/rel/vendas-pdf")
	public void gerarRelatorioVendas(@ModelAttribute("filtro") Filtros filtro, HttpServletResponse response,
			HttpServletRequest request) {
//		System.out.println(request.getParameter("dtInicio"));
//		System.out.println(filtro.getDtInicio().substring(0, 10));
//		System.out.println(filtro.getDtFim());
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			HashMap<String, Object> p = new HashMap<>();
			p.put("dtInicio", getDataFormatada(filtro.getDtInicio()));
			p.put("dtFim", getDataFormatada(filtro.getDtFim()));

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
	
	private Date getDataFormatada(String dataString) throws ParseException {
		SimpleDateFormat modelo = new SimpleDateFormat("yyyy-MM-dd");
		return modelo.parse(dataString);
	}
}
