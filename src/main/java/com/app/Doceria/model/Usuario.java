package com.app.Doceria.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long usuario_id;
	@ManyToOne
	private Funcionario funcionario;
	@ManyToOne
	private Cliente cliente;
	@ManyToOne
	private PermissoesFuncionario permissoes;
	public long getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(long usuario_id) {
		this.usuario_id = usuario_id;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public PermissoesFuncionario getPermissoes() {
		return permissoes;
	}
	public void setPermissoes(PermissoesFuncionario permissoes) {
		this.permissoes = permissoes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
