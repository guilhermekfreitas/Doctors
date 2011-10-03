package br.com.doctors.controller.administracao;

import br.com.caelum.vraptor.*;
import br.com.doctors.dao.administracao.PerfilUsuarioDao;
import br.com.doctors.modelo.administracao.PerfilUsuario;
import br.com.doctors.modelo.administracao.UsuarioInvalidoException;
import br.com.doctors.util.UserSession;

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
	
//	public void index(){
//		
//	}
	
	@Path("/teste")
	public void teste(){
		
	}
	
	@Get
	@Path("/login")
	public void login(){
		//result.redirectTo(this).index();
	}

	// deprecated???
	@Post
	@Path("/logar")
	public void logar(PerfilUsuario perfilUsuario, String j_username, String j_password){ // convencao spring security
		
		System.out.println("Logou.." + j_username);
		System.out.println(perfilUsuario);
		
//		validator.checking(perfilUsuario.getValidations());
//		validator.onErrorUsePageOf(this).login();
		
		// procura por usu�rio
		PerfilUsuario user = null;
		try {
			user = usuarioDao.logar(perfilUsuario);

			userSession.carregaUsuario(user);
			result.redirectTo("/");      // redireciona p�gina principal
		} catch (UsuarioInvalidoException e) {
			e.printStackTrace();
			// adicionando temporariamente
			//result.include("errors",new Object[]{"Login e/ou senha inv�lidos."});
			result.forwardTo(this).login();
		}
	
	}
	
	@Path("/logado")
	public void logado(){
		result.redirectTo("/");
	}
	
	@Get
	@Path("/logout")
	public void logout(){
		userSession.carregaUsuario(null);
		result.redirectTo("/");
	}
	
}
