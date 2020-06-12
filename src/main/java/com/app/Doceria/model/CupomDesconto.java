package com.app.Doceria.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CupomDesconto implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nomecupom;
	private String numerocupom;
	@ManyToOne
	private Funcionario funcionario;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNomecupom() {
		return nomecupom;
	}
	public void setNomecupom(String nomecupom) {
		this.nomecupom = nomecupom;
	}
	public String getNumerocupom() {
		return numerocupom;
	}
	public void setNumerocupom(String numerocupom) {
		this.numerocupom = numerocupom;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
