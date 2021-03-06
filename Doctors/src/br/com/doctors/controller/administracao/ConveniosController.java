package br.com.doctors.controller.administracao;


import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;

import br.com.doctors.dao.administracao.ConvenioDao;
import br.com.doctors.modelo.administracao.Convenio;

/***
 * 
 * @author Guilherme/Jonathan
 *
 */
@Resource
public class ConveniosController {
	private ConvenioDao dao;
	private Result result;
	private Validator validator;

	public ConveniosController(ConvenioDao dao, Result result, Validator validator ) {
		this.dao = dao;
		this.result = result;
		this.validator = validator;
		
		
	}
	
	public void index(){
		
	}
	
	@Get @Path("/convenios/novo")
	public void cadastro(){
	}
	
	@Get @Path({"/convenios"})
	public void list(){
		result.include("convenios", dao.listaTudo());
	}
	
	@Post @Path("/convenios")
	public void adiciona(final Convenio convenio){
		
		validator.checking(getValidations(convenio));
		validator.onErrorUsePageOf(this).cadastro();
		
		dao.adiciona(convenio);
		result.redirectTo(this).list();
	}
	
	
	@Get @Path("/convenios/{id}")
	public Convenio edit(Long id){
		Convenio c = dao.carrega(id);
		System.out.println(c);
		return c;
	}
	
	@Put @Path("/convenios/{convenio.id}")
	public void alterar(final Convenio convenio){
		validator.checking(getValidations(convenio));
		validator.onErrorUsePageOf(this).edit(convenio.getId());
		
		dao.atualiza(convenio);
		result.redirectTo(ConveniosController.class).list();
	}
	
	@Path("/convenios/remover/{id}")
	public void remover(Long id){
		Convenio convenio = dao.carrega(id);
		dao.remove(convenio);
		result.redirectTo(ConveniosController.class).list();
	}
	
	public void buscaForm(){
		
	}
	
	public void busca(String nome){
		result.include("nome", nome);
		result.include("resultados", dao.busca(nome) );
	}

	private Validations getValidations(final Convenio convenio) {
		return new Validations(){{
			that(convenio.getNome() != null && convenio.getNome().length() >= 3, 
					"convenio.nome", "nome.obrigatorio");
		}};
	}
	
	
}
