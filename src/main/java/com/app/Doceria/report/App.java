package com.app.Doceria.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.app.Doceria.service.EstadoReportService;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public String generateReport(final EstadoReportService estadoReportService) {

		String msg = estadoReportService.generateReport();

		System.out.println(msg);

		return msg;
	}
}
