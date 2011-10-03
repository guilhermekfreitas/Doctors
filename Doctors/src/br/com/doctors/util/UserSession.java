package br.com.doctors.util;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.doctors.modelo.administracao.PerfilUsuario;

@Component
@SessionScoped
public class UserSession {
	private PerfilUsuario usuario;

	public PerfilUsuario getUsuario() {
		return usuario;
	}

	public void carregaUsuario(PerfilUsuario usuario) {
		this.usuario = usuario;
	}

	public boolean hasUsuario() {
		return usuario != null;
	}
}
