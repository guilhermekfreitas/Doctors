package br.com.doctors.interceptors;

import java.util.Arrays;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.doctors.UserSession;
import br.com.doctors.controller.administracao.LoginController;
import br.com.doctors.controller.administracao.PacientesController;

@Intercepts
@RequestScoped
public class LoginInterceptor implements Interceptor{
	
	private Result result;
	private UserSession userSession;

	public LoginInterceptor(Result result, UserSession userSession) {
		this.result = result;
		this.userSession = userSession;
	}

	@Override
	public boolean accepts(ResourceMethod method) {
//		return !Arrays.asList(LoginController.class).contains(method.getMethod().getDeclaringClass());
		return !Arrays.asList(PacientesController.class,LoginController.class).contains(method.getMethod().getDeclaringClass());
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {
		if (userSession.getUsuario() != null) {
	        stack.next(method, resourceInstance);
	    } else {
	        result.redirectTo(LoginController.class).login();
	    }
	}
}
