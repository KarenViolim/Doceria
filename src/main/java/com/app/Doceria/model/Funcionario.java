package com.app.Doceria.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="funcionario")
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable=false, length=250)
	@NotEmpty(message="O nome é obrigatório")
	private String nome;
	@CPF(message = "O CPF É INVÁLIDO!")
	@NotEmpty(message="O CPF é obrigatório")
	private String cpf;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataNascimento;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataEntrada;
	@ManyToOne 
	private Papeis papeis;
	private Double salarioBruto;
	private String telefone;
	private String email;
	private String senha;
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Cidade cidade;
	private String endereco;
	private String numero;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Date getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public Papeis getPapeis() {
		return papeis;
	}
	public void setPapeis(Papeis papeis) {
		this.papeis = papeis;
	}
	public Double getSalarioBruto() {
		return salarioBruto;
	}
	public void setSalarioBruto(Double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

}