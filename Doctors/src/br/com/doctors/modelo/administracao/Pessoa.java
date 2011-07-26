package br.com.doctors.modelo.administracao;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Guilherme
 *
 */

@MappedSuperclass
public abstract class Pessoa {

	private String nome;
	private String telefone;
	private String endereco;
	private String cpf;
	private String email;
	@Temporal(TemporalType.DATE)
	private Date dataDeNascimento;
	private String login;
	private String senha;
	
	public String getNome() {
		return nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public String getEndereco() {
		return endereco;
	}
	public String getCpf() {
		return cpf;
	}
	public String getEmail() {
		return email;
	}
	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}
	public String getLogin() {
		return login;
	}
	public String getSenha() {
		return senha;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void format(String date){
		DateFormat df = DateFormat.getDateInstance();
		try {
			dataDeNascimento = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
