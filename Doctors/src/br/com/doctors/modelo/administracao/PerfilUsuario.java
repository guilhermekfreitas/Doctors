package br.com.doctors.modelo.administracao;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.doctors.modelo.util.TipoPerfil;

@Entity
@Table(name="usuarios")
public class PerfilUsuario {
	@Id @GeneratedValue
	private Long id;
	private String login;
	private String senha;
	@Enumerated(EnumType.STRING)
	private TipoPerfil tipo;
	
	public PerfilUsuario() {
		// TODO Auto-generated constructor stub
	}	
	
	public PerfilUsuario(String login, String senha ){
		this.login = login;
		this.senha = senha;
	}
	
	public String getLogin() {
		return login;
	}
	public String getSenha() {
		return senha;
	}
	public TipoPerfil getTipo() {
		return tipo;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setTipo(TipoPerfil tipo) {
		this.tipo = tipo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return String.format("Login: %s Senha: %s TipoPerfil:%s", login, senha, tipo);
	}

	public boolean isPaciente() {
		return tipo == TipoPerfil.ROLE_PACIENTE;
	}
	
	public boolean isMedico(){
		return tipo == TipoPerfil.ROLE_MEDICO;
	}
	
	public boolean isFuncionario(){
		return tipo == TipoPerfil.ROLE_FUNCIONARIO;
	}
	
	public boolean isAdmin(){
		return tipo == TipoPerfil.ROLE_ADMIN;
	}
}

