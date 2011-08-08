package br.com.doctors.controller.administracao;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
 
@Resource
public class AdminController {
    //Adicionamos o request no construtor para obter os dados do usu�rio logado
    private HttpServletRequest request;
	private Result result;
 
    public AdminController(HttpServletRequest request, Result result) {
        this.request = request;
        this.result = result;
    }
 
    @Path("/admin")
    public void index() {
        Principal user = request.getUserPrincipal(); //Aqui o usu�rio logado � obtido
        System.out.println(user.getName()); // getName() retorna o e-mail do usu�rio
        
        result.include("nomeUsuario", user.getName());
    }
}
