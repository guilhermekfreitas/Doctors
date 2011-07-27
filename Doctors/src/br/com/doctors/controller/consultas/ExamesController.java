package br.com.doctors.controller.consultas;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.doctors.dao.consultas.ExameDao;
import br.com.doctors.modelo.consultas.Exame;

/**
 * 
 * @author Jonathan/Guilherme
 *
 */
@Resource
public class ExamesController {

	private ExameDao dao;
	private Result result;
	private Validator validator;

	public ExamesController(ExameDao dao, Result result, Validator validator) {
		this.dao = dao;
		this.result = result;
		this.validator = validator;
	}
	
	@Get @Path({"/exames"})
	public void list() {
		result.include("exames", dao.listaTudo());
	}
	
	@Get @Path("/exames/novo")
	public void cadastro() {}
	
	@Post @Path("/exames")
	public void adiciona(final Exame exame){
		validator.checking(new Validations(){{
			that(exame.getDescricao() != null && exame.getDescricao().length() >= 3, 
					"exame.nome", "descricao.obrigatorio");
		}});
		validator.onErrorUsePageOf(this).cadastro();
		
		dao.adiciona(exame);
		result.redirectTo(ExamesController.class).list();
	}
	
	@Get @Path("/exames/{id}")
	public Exame edit(Long id){
		return dao.carrega(id);
	}
	
	@Put @Path("/exames/{exame.id}")
	public void alterar(final Exame exame){
		validator.checking(new Validations(){{
			that(exame.getDescricao() != null && exame.getDescricao().length() >= 3, 
					"exame.nome", "descricao.obrigatorio");
		}});
		validator.onErrorUsePageOf(this).edit(exame.getId());
		
		dao.atualiza(exame);
		result.redirectTo(ExamesController.class).list();
	}
	
	@Path("/exames/remover/{id}")
	public void remover(Long id){
		Exame exame = dao.carrega(id);
		dao.remove(exame);
		result.redirectTo(ExamesController.class).list();
	}
	
}
