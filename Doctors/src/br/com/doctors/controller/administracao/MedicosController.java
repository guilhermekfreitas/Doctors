package br.com.doctors.controller.administracao;

import javax.annotation.security.RolesAllowed;

import com.google.common.base.Strings;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.doctors.dao.administracao.ConvenioDao;
import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.administracao.PerfilUsuarioDao;
import br.com.doctors.modelo.administracao.Medico;

/***
 * 
 * @author Bruno
 *
 */
@Resource
public class MedicosController {
	private Result result;
	private Validator validator;
	private MedicoDao daoMedico;
	private PerfilUsuarioDao daoPerfil;

	public MedicosController(MedicoDao daoMedico, Result result, PerfilUsuarioDao daoPerfilUsuario, 
			Validator validator) {
		this.daoMedico = daoMedico;
		this.result = result;
		this.validator = validator;
		this.daoPerfil = daoPerfilUsuario;
	}
	
	@Get @Path({"/medicos"})
	public void list() {
		result.include("medicos", daoMedico.listaTudo());
	}
	
	@Get @Path("/medicos/novo")
	public void cadastro() {
	}
	
	@Post @Path("/medicos")
	public void adiciona(final Medico medico){
		
//		System.out.println("-======================================");
//		System.out.println("Medico:" + medico + medico.getUfRegistro());
		
		validator.checking(getCadastroValidations(medico));
		validator.onErrorUsePageOf(this).cadastro();
		
		daoPerfil.adiciona(medico.getPerfil());
		daoMedico.adiciona(medico);
		result.redirectTo(MedicosController.class).list();
	}

	private Validations getCadastroValidations(final Medico medico) {
		return new Validations(){{
			that(medico.getNome() != null && medico.getNome().length() >= 3, 
					"medico.nome", "nome.obrigatorio");
			that(medico.getCrm() != null, 
					"medico.crm", "campo.obrigatorio", "CRM");
			that(!Strings.isNullOrEmpty(medico.getUfRegistro()), 
					"medico.ufRegistro", "campo.obrigatorio", "relacionado ao UF de registro do médico");
			that(!Strings.isNullOrEmpty(medico.getEspecialidade()), 
					"medico.especialidade", "campo.obrigatorio", "Especialidade");
			that(!Strings.isNullOrEmpty(medico.getPerfil().getLogin() ), 
					"medico.perfil.login", "campo.obrigatorio", "Login");
			that(!Strings.isNullOrEmpty(medico.getPerfil().getSenha()), 
					"medico.perfil.senha", "campo.obrigatorio", "Senha");
			that(!daoPerfil.loginJaExiste(medico.getPerfil().getLogin()),
					"medico.perfil.login", "login.ja.existe", medico.getPerfil().getLogin());
		}};
	}
	
	@Get @Path("/medicos/{id}")
	public Medico edit(Long id){
		return daoMedico.carrega(id);
	}
	
	@Put @Path("/medicos/{medico.id}")
	public void alterar(final Medico medico){
		
		validator.checking(getEdicaoValidations(medico));
		validator.onErrorUsePageOf(this).edit(medico.getId());
		
		medico.setPerfil(daoPerfil.carrega(medico.getPerfil().getId()));
		daoMedico.atualiza(medico);
		result.redirectTo(MedicosController.class).list();
	}

	private Validations getEdicaoValidations(final Medico medico) {
		return new Validations(){{
			that(medico.getNome() != null && medico.getNome().length() >= 3, 
					"medico.nome", "nome.obrigatorio");
			that(medico.getCrm() != null, 
					"medico.crm", "campo.obrigatorio", "CRM");
			that(!Strings.isNullOrEmpty(medico.getUfRegistro()), 
					"medico.ufRegistro", "campo.obrigatorio", "relacionado ao UF de registro do médico");
			that(!Strings.isNullOrEmpty(medico.getEspecialidade()), 
					"medico.especialidade", "campo.obrigatorio", "Especialidade");
		}};
	}
	
	@Path("/medicos/remover/{id}")
	public void remover(Long id){
		Medico medico = daoMedico.carrega(id);
		daoMedico.remove(medico);
		result.redirectTo(MedicosController.class).list();
	}
}
