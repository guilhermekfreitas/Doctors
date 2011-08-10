package br.com.doctors.controller.administracao;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.doctors.util.Configuracao;
import br.com.doctors.util.ConfiguracaoFactory;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
 
@Resource
public class AdminController {
    //Adicionamos o request no construtor para obter os dados do usuário logado
    private HttpServletRequest request;
	private Result result;
 
    public AdminController(HttpServletRequest request, Result result) {
        this.request = request;
        this.result = result;
    }
 
    @Path("/painel_admin")
    public void index() {
        Principal user = request.getUserPrincipal(); //Aqui o usuário logado é obtido
        System.out.println(user.getName()); // getName() retorna o e-mail do usuário
        
        result.include("nomeUsuario", user.getName());
    }
    
    @Path("/painel_admin/configuracoes")
    public void configuracoes(){
    	Configuracao conf = ConfiguracaoFactory.getConfiguracao();
    	Collection<Entry<Object, Object>> parametros = conf.getAllPropriedades(); 
    	result.include("parametros", parametros);
    }
    
}
