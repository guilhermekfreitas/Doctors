package br.com.doctors.controller;


import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import br.com.doctors.dao.ConvenioDao;
import br.com.doctors.modelo.Convenio;

@Resource
public class ConveniosController {
	private ConvenioDao dao;
	private Result result;

	public ConveniosController(ConvenioDao dao, Result result) {
		this.dao = dao;
		this.result = result;
	}
	
	@Path("/convenios/")
	public void index () {
		
	}
	
	public void adiciona(Convenio convenio){
		dao.adiciona(convenio);
	}
	
	public void form(){
		
	}
	
	public void buscaForm(){
		
	}
	
	public void busca(String nome){
		result.include("nome", nome);
		result.include("resultados", dao.busca(nome) );
	}
	
	public void list(){
		result.include("convenios", dao.listaTodos());
	}
}
