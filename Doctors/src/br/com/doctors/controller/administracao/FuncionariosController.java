package br.com.doctors.controller.administracao;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.doctors.dao.administracao.FuncionarioDao;
import br.com.doctors.modelo.administracao.Funcionario;

/**
 * 
 * @author Jonathan/Guilherme
 *
 */
@Resource
public class FuncionariosController {
	private Result result;
	private Validator validator;
	private FuncionarioDao dao;

	public FuncionariosController(FuncionarioDao dao, Result result, Validator validator) {
		this.dao = dao;
		this.result = result;
		this.validator = validator;
	}
	
	@Get @Path({"/funcionarios"})
	public void list() {
		result.include("funcionarios", dao.listaTudo());
	}
	
	@Get @Path("/funcionarios/novo")
	public void cadastro() {
	}
	
	@Post @Path("/funcionarios")
	public void adiciona(final Funcionario funcionario){
		
		System.out.println("-======================================");
		System.out.println("Funcionario:" + funcionario );
		
		validator.checking(funcionario.getValidations());
		validator.onErrorUsePageOf(this).cadastro();
		
		dao.adiciona(funcionario);
		result.redirectTo(FuncionariosController.class).list();
	}
	
	@Get @Path("/funcionarios/{id}")
	public Funcionario edit(Long id){
		return dao.carrega(id);
	}
	
	@Put @Path("/funcionarios/{funcionario.id}")
	public void alterar(final Funcionario funcionario){
		
		validator.checking(funcionario.getValidations());
		validator.onErrorUsePageOf(this).edit(funcionario.getId());
		
		dao.atualiza(funcionario);
		result.redirectTo(FuncionariosController.class).list();
	}
	
	@Path("/funcionarios/remover/{id}")
	public void remover(Long id){
		Funcionario funcionario = dao.carrega(id);
		dao.remove(funcionario);
		result.redirectTo(FuncionariosController.class).list();
	}
}
