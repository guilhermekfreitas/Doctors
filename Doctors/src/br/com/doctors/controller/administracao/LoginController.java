package br.com.doctors.controller.administracao;

import br.com.caelum.vraptor.*;
import br.com.doctors.UserSession;
import br.com.doctors.dao.administracao.PerfilUsuarioDao;
import br.com.doctors.modelo.administracao.PerfilUsuario;

@Resource
public class LoginController {

	private PerfilUsuarioDao usuarioDao;
	private Result result;
	private Validator validator;
	private UserSession userSession;

	public LoginController(PerfilUsuarioDao usuarioDao, Result result, Validator validator,
			UserSession userSession) {
		this.usuarioDao = usuarioDao;
		this.result = result;
		this.validator = validator;
		this.userSession = userSession;
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
		
		// procura por usu�rio
		PerfilUsuario user = null;
		try {
			user = usuarioDao.logar(perfilUsuario);

			userSession.setUsuario(user);
			result.redirectTo("/");      // redireciona p�gina principal
		} catch (Exception e) {
			e.printStackTrace();
			// adicionando temporariamente
			//result.include("errors",new Object[]{"Login e/ou senha inv�lidos."});
			result.forwardTo(this).login();
		}
	
	}
	
	@Get
	@Path("/logout")
	public void logout(){
		userSession.setUsuario(null);
		result.redirectTo("/");
	}
}
