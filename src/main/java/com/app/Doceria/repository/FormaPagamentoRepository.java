package com.app.Doceria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Doceria.model.FormaDePagamento;


public interface FormaPagamentoRepository extends JpaRepository<FormaDePagamento, Long> {

}