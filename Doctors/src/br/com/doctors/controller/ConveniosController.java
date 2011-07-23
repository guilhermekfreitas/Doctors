package br.com.doctors.controller;


import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;

import br.com.doctors.dao.ConvenioDao;
import br.com.doctors.modelo.Convenio;

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
	
	@Path("/convenios/")
	public void index () {
	}
	
	public void cadastro(){
	}
	
	@Post
	public void adiciona(final Convenio convenio){
		
		// hibernate validator -> validator.validate(convenio);
		
		validator.checking(new Validations(){{
			that(convenio.getNome() != null && convenio.getNome().length() >= 3, 
					"convenio.nome", "nome.obrigatorio");
		}});
		validator.onErrorUsePageOf(this).cadastro();
		
		dao.adiciona(convenio);
		result.redirectTo(ConveniosController.class).list();
	}
	
	public void list(){
		result.include("convenios", dao.listaTodos());
	}
	
	public Convenio edit(Long id){
		Convenio c = dao.carrega(id);
		System.out.println(c);
		return c;
	}
	
	public void alterar(final Convenio convenio){
		validator.checking(new Validations(){{
			that(convenio.getNome() != null && convenio.getNome().length() >= 3, 
					"convenio.nome", "nome.obrigatorio");
		}});
		validator.onErrorUsePageOf(this).edit(convenio.getId());
		
		dao.atualiza(convenio);
		result.redirectTo(ConveniosController.class).list();
	}
	
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
	
}
