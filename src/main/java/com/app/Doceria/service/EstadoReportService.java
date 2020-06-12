package com.app.Doceria.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class EstadoReportService {

	public String generateReport() {
		try {

			String reportPath = "C:\\resources\\report";

			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(reportPath + "\\report\\listagemDocumentos.jrxml");

			// Get your data source
			DriverManagerDataSource dataSource = new DriverManagerDataSource();

			// Add parameters
			Map<String, Object> parameters = new HashMap<>();

			parameters.put("Criado por", "Karen Cristina");

			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					dataSource.getConnection());

			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "\\report\\listagemDocumentos.pdf");

			System.out.println("Finalizado");

			return "Report gerado com sucesso @path= " + reportPath;

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
