package br.com.doctors.controller.administracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Paciente;

@Resource
public class PacientesController {
	private PacienteDao daoPacientes;
	private Result result;
	private Validator validator;
	private ConvenioDao daoConvenio;

	public PacientesController(ConvenioDao daoConvenio, PacienteDao daoPaciente, Result result, Validator validator) {
		this.daoPacientes = daoPaciente;
		this.result = result;
		this.validator = validator;
		this.daoConvenio = daoConvenio;
	}
	
	@Get @Path({"/pacientes"})
	public void list() {
		result.include("pacientes", daoPacientes.listaTudo());
	}
	
	@Get @Path("/pacientes/novo")
	public void cadastro() {
		result.include("convenios", daoConvenio.listaTodos() );
	}
	
	@Post @Path("/pacientes")
	public void adiciona(final Paciente paciente, Collection<Long> conveniosId){
		
		System.out.println(conveniosId);
		
		// recuperar cada id, e adicionar ao paciente
		List<Convenio> convenios = new ArrayList<Convenio>();
		for( Long id : conveniosId ){
			convenios.add(daoConvenio.carrega(id));
		}
		
		paciente.setConvenios(convenios);
		
		System.out.println("-======================================");
		System.out.println("Paciente:" + paciente + paciente.getConvenios());
		
		validator.checking(new Validations(){{
			that(paciente.getNome() != null && paciente.getNome().length() >= 3, 
					"paciente.nome", "nome.obrigatorio");
			
			// mais validações
		}});
		validator.onErrorUsePageOf(this).cadastro();
		
		daoPacientes.adiciona(paciente);
		result.redirectTo(PacientesController.class).list();
	}
	
	@Get @Path("/pacientes/{id}")
	public Paciente edit(Long id){
		result.include("convenios", daoConvenio.listaTodos() );
		return daoPacientes.carrega(id);
	}
	
	@Put @Path("/pacientes/{paciente.id}")
	public void alterar(final Paciente paciente){
		validator.checking(new Validations(){{
			that(paciente.getNome() != null && paciente.getNome().length() >= 3, 
					"paciente.nome", "nome.obrigatorio");
			
			// mais validações
		}});
		validator.onErrorUsePageOf(this).edit(paciente.getId());
		
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
