package br.com.doctors.modelo.administracao;

import javax.persistence.*;

import com.google.common.base.Strings;

import br.com.caelum.vraptor.validator.Validations;
import br.com.doctors.modelo.util.TipoPerfil;

@Entity
@Table(name="usuarios")
public class PerfilUsuario {
	@Id @GeneratedValue
	private Long id;
	private String login;
	private String senha;
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
	
	public Validations getValidations(){
		return new Validations(){{
			that(!Strings.isNullOrEmpty(PerfilUsuario.this.login ), 
					"perfilUsuario.login", "campo.obrigatorio", "Login");
			that(!Strings.isNullOrEmpty(PerfilUsuario.this.senha ), 
					"perfilUsuario.senha", "campo.obrigatorio", "Senha");
		}};
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
