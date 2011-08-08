package br.com.doctors.interceptors;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.doctors.UserSession;
import br.com.doctors.dao.administracao.PerfilUsuarioDao;
import br.com.doctors.modelo.administracao.PerfilUsuario;

@Deprecated
@Intercepts
@RequestScoped
public class UsuarioInterceptor implements Interceptor {
	private HttpServletRequest request;
	private Result result;
	private UserSession userSession;
	private PerfilUsuarioDao dao;

	public UsuarioInterceptor(HttpServletRequest request, Result result, UserSession userSession, PerfilUsuarioDao dao ) {
		this.request = request;
		this.result = result;
		this.userSession = userSession;
		this.dao = dao;
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return true;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {
		// TODO Auto-generated method stub
		
		if (request.getUserPrincipal() != null && !userSession.hasUsuario()){
			PerfilUsuario usuario = dao.carrega(request.getUserPrincipal().getName()) ;
			userSession.setUsuario(usuario);
			System.out.printf("Adicionando usuario [%s] a sessao.", usuario);
		} else {
			System.out.println("Interceptando -->" + request.getUserPrincipal());
			if (!userSession.hasUsuario()){
				System.out.println("SEM USUARIO NA SESSÃO");
			} else {
				System.out.println("USUARIO:" + userSession.getUsuario());
			}
		}
		stack.next(method, resourceInstance);
	}
}
