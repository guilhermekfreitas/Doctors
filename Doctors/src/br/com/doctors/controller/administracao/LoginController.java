package br.com.doctors.controller.administracao;

import br.com.caelum.vraptor.*;
import br.com.doctors.dao.administracao.PerfilUsuarioDao;
import br.com.doctors.modelo.administracao.PerfilUsuario;

@Resource
public class LoginController {

	private PerfilUsuarioDao usuarioDao;
	private Result result;
	private Validator validator;

	public LoginController(PerfilUsuarioDao usuarioDao, Result result, Validator validator) {
		this.usuarioDao = usuarioDao;
		this.result = result;
		this.validator = validator;
	}
	
	@Get
	@Path("/login")
	public void login(){
	}

	@Post
	@Path("/login")
	public void logar(PerfilUsuario perfilUsuario){
		
		validator.checking(perfilUsuario.getValidations());
		validator.onErrorUsePageOf(this).login();
		
		// procura por usuário
		PerfilUsuario user = null;
		try {
			user = usuarioDao.logar(perfilUsuario);
		} catch (Exception e) {
			e.printStackTrace();
			// adicionando temporariamente
			result.include(new Object[]{"Login e/ou senha inválidos."});
			result.redirectTo(this).login();
		}
	
		// adicionar na sessão
		// redirecionar para pagina inicial
		
	}
}
