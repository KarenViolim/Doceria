package com.app.Doceria.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="permissoes_funcionario")
public class PermissoesFuncionario implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Funcionario funcionario;
	@ManyToOne
	private Papeis papeis;
	@Temporal(TemporalType.DATE)
	private Date dataPermissao = new Date();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Papeis getPapeis() {
		return papeis;
	}
	public void setPapeis(Papeis papeis) {
		this.papeis = papeis;
	}
	public Date getDataPermissao() {
		return dataPermissao;
	}
	public void setDataPermissao(Date dataPermissao) {
		this.dataPermissao = dataPermissao;
	}
}
