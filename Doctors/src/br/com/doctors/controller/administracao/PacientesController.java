package br.com.doctors.controller.administracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import br.com.doctors.controller.administracao.PacientesController;
import br.com.doctors.dao.administracao.ConvenioDao;
import br.com.doctors.dao.administracao.PacienteDao;
import br.com.doctors.dao.administracao.PerfilUsuarioDao;
import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Paciente;

/***
 * 
 * @author Renato
 *
 */
@Resource
public class PacientesController {
	private PacienteDao daoPacientes;
	private Result result;
	private Validator validator;
	private ConvenioDao daoConvenio;
	private PerfilUsuarioDao daoPerfilUsuario;

	public PacientesController(ConvenioDao daoConvenio, PacienteDao daoPaciente, PerfilUsuarioDao daoPerfilUsuario,
			       Result result, Validator validator) {
		this.daoPacientes = daoPaciente;
		this.result = result;
		this.validator = validator;
		this.daoConvenio = daoConvenio;
		this.daoPerfilUsuario = daoPerfilUsuario;
	}
	
	@Get @Path({"/pacientes"})
	public void list() {
		result.include("pacientes", daoPacientes.listaTudo());
	}
	
	@Get @Path("/pacientes/novo")
	public void cadastro() {
		result.include("convenios", daoConvenio.listaTudo() );
	}
	
	@Post @Path("/pacientes")
	public void adiciona(final Paciente paciente, Collection<Long> conveniosId, String opcaoConvenios){
		
		System.out.println("Tipo de Perfil: " + paciente.getPerfil());
		
		if (conveniosId != null && opcaoConvenios.equalsIgnoreCase("conveniado")){
			// recuperar cada id, e adicionar ao paciente
			List<Convenio> conveniosList = new ArrayList<Convenio>();
			for( Long id : conveniosId ){
				conveniosList.add(daoConvenio.carrega(id));
			}
			paciente.setConvenios(conveniosList);
		}
		validator.checking(new Validations(){{
			that(paciente.getNome() != null && paciente.getNome().length() >= 3, 
					"paciente.nome", "nome.obrigatorio");
			that(!Strings.isNullOrEmpty(paciente.getPerfil().getLogin() ), 
					"paciente.perfil.login", "campo.obrigatorio", "Login");
			that(!Strings.isNullOrEmpty(paciente.getPerfil().getSenha()), 
					"paciente.perfil.senha", "campo.obrigatorio", "Senha");
			that(!daoPerfilUsuario.loginJaExiste(paciente.getPerfil().getLogin()),
					"paciente.perfil.login", "login.ja.existe", paciente.getPerfil().getLogin());
		}});
		validator.onErrorUsePageOf(this).cadastro();
		
		daoPerfilUsuario.adiciona(paciente.getPerfil());
		daoPacientes.adiciona(paciente);
		result.redirectTo(PacientesController.class).list();
	}
	
	@Get @Path("/pacientes/{id}")
	public Paciente edit(Long id){
		result.include("convenios", daoConvenio.listaTudo() );
		return daoPacientes.carrega(id);
	}
	
	@Put @Path("/pacientes/{paciente.id}")
	public void alterar(final Paciente paciente, Collection<Long> conveniosId, String opcaoConvenios){

		if (conveniosId != null && opcaoConvenios.equalsIgnoreCase("conveniado")){
			// recuperar cada id, e adicionar ao paciente
			List<Convenio> convenios = new ArrayList<Convenio>();
			for( Long id : conveniosId ){
				convenios.add(daoConvenio.carrega(id));
			}
			paciente.setConvenios(convenios);
		}
		
		validator.checking(new Validations(){{
			that(paciente.getNome() != null && paciente.getNome().length() >= 3, 
					"paciente.nome", "nome.obrigatorio");
		}});
		validator.onErrorUsePageOf(this).edit(paciente.getId());
		
		System.out.println(paciente.getPerfil());
		System.out.println("---------------------------------");
		
		paciente.setPerfil(daoPerfilUsuario.carrega(paciente.getPerfil().getId()));
		daoPacientes.atualiza(paciente);
		result.redirectTo(PacientesController.class).list();
	}
	
	@Path("/pacientes/remover/{id}")
	public void remover(Long id){
		Paciente paciente = daoPacientes.carrega(id);
		daoPacientes.remove(paciente);
		result.redirectTo(PacientesController.class).list();
	}
}
