package br.com.doctors.controller.administracao;

import com.google.common.base.Strings;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.doctors.dao.administracao.FuncionarioDao;
import br.com.doctors.dao.administracao.PerfilUsuarioDao;
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
	private FuncionarioDao daoFuncionario;
	private PerfilUsuarioDao daoPerfil;

	public FuncionariosController(FuncionarioDao daoFuncionario, PerfilUsuarioDao daoPerfil, Result result, Validator validator) {
		this.daoFuncionario = daoFuncionario;
		this.daoPerfil = daoPerfil;
		this.result = result;
		this.validator = validator;
	}
	
	@Get @Path({"/funcionarios"})
	public void list() {
		result.include("funcionarios", daoFuncionario.listaTudo());
	}
	
	@Get @Path("/funcionarios/novo")
	public void cadastro() {
	}
	
	@Post @Path("/funcionarios")
	public void adiciona(final Funcionario funcionario){
		
		System.out.println("-======================================");
		System.out.println("Funcionario:" + funcionario );
		
		validator.checking(getCadastroValidations(funcionario));
		validator.onErrorUsePageOf(this).cadastro();
		
		daoPerfil.adiciona(funcionario.getPerfil());
		daoFuncionario.adiciona(funcionario);
		result.redirectTo(FuncionariosController.class).list();
	}

	private Validations getCadastroValidations(final Funcionario funcionario) {
		return new Validations(){{
			that(funcionario.getNome() != null && funcionario.getNome().length() >= 3, 
					"funcionario.nome", "nome.obrigatorio");
			that(funcionario.getMatricula() != null, 
					"funcionario.matricula", "campo.obrigatorio", "número de matrícula");
			that(!Strings.isNullOrEmpty(funcionario.getDataAdmissao()), 
					"funcionario.dataAdmissao", "campo.obrigatorio", "data de admissão");
			that(!Strings.isNullOrEmpty(funcionario.getPerfil().getLogin() ), 
					"funcionario.perfil.login", "campo.obrigatorio", "Login");
			that(!Strings.isNullOrEmpty(funcionario.getPerfil().getSenha()), 
					"funcionario.perfil.senha", "campo.obrigatorio", "Senha");
			that(!daoPerfil.loginJaExiste(funcionario.getPerfil().getLogin()),
					"funcionario.perfil.login", "login.ja.existe", funcionario.getPerfil().getLogin());
		}};
	}
	
	@Get @Path("/funcionarios/{id}")
	public Funcionario edit(Long id){
		return daoFuncionario.carrega(id);
	}
	
	@Put @Path("/funcionarios/{funcionario.id}")
	public void alterar(final Funcionario funcionario){
		
		System.out.println("-======================================");
		System.out.println("Funcionario:" + funcionario.getPerfil() );
		
		validator.checking(getEdicaoValidations(funcionario));
		validator.onErrorUsePageOf(this).edit(funcionario.getId());
		
		funcionario.setPerfil(daoPerfil.carrega(funcionario.getPerfil().getId()));
		daoFuncionario.atualiza(funcionario);
		result.redirectTo(FuncionariosController.class).list();
	}

	private Validations getEdicaoValidations(final Funcionario funcionario) {
		return new Validations(){{
			that(funcionario.getNome() != null && funcionario.getNome().length() >= 3, 
					"funcionario.nome", "nome.obrigatorio");
			that(funcionario.getMatricula() != null, 
					"funcionario.matricula", "campo.obrigatorio", "número de matrícula");
			that(!Strings.isNullOrEmpty(funcionario.getDataAdmissao()), 
					"funcionario.dataAdmissao", "campo.obrigatorio", "data de admissão");
		}};
	}
	
	@Path("/funcionarios/remover/{id}")
	public void remover(Long id){
		Funcionario funcionario = daoFuncionario.carrega(id);
		daoFuncionario.remove(funcionario);
		result.redirectTo(FuncionariosController.class).list();
	}
}
