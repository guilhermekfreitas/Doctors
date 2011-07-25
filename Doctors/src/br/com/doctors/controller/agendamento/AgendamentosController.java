package br.com.doctors.controller.agendamento;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.doctors.controller.agendamento.AgendamentosController;
import br.com.doctors.dao.administracao.ConvenioDao;
import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.administracao.PacienteDao;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.agendamento.Agendamento;

@Resource
public class AgendamentosController {
	private Result result;
	private Validator validator;
	private AgendamentoDao daoAgendamento;
	private PacienteDao daoPaciente;
	private MedicoDao daoMedico;
	private ConvenioDao daoConvenio;

	public AgendamentosController(AgendamentoDao daoAgendamento, Result result, 
			Validator validator, PacienteDao daoPaciente, MedicoDao daoMedico,
			ConvenioDao daoConvenio) {
		this.daoAgendamento = daoAgendamento;
		this.result = result;
		this.validator = validator;
		this.daoPaciente = daoPaciente;
		this.daoMedico = daoMedico;
		this.daoConvenio = daoConvenio;
	}
	
	@Get @Path({"/agenda"})
	public void list() {
		result.include("agendamentos", daoAgendamento.listaTudo());
	}
	
	@Get @Path("/agenda/novo")
	public void cadastro() {
		result.include("medicos", daoMedico.listaTudo());
		result.include("pacientes", daoPaciente.listaTudo());
	}
	
	@Post @Path("/agenda")
	public void adiciona(final Agendamento agendamento){
		
		System.out.println("Agendamento:" + agendamento );
		
		System.out.println("Paciente id:" + agendamento.getPaciente().getId());
		List<Convenio> convenios = daoConvenio.buscaPor(agendamento.getPaciente().getId());
		System.out.println("Convenios de Paciente: " + agendamento.getPaciente().getId());
		for( Convenio c: convenios)
			System.out.println(c);
		
		daoAgendamento.adiciona(agendamento);
		result.redirectTo(AgendamentosController.class).list();
	}
	
	@Get @Path("/agenda/{id}")
	public Agendamento edit(Long id){
		return daoAgendamento.carrega(id);
	}
	
	@Put @Path("/agenda/{agendamento.id}")
	public void alterar(final Agendamento agendamento){
		
		daoAgendamento.atualiza(agendamento);
		result.redirectTo(AgendamentosController.class).list();
	}
	
	@Path("/agenda/remover/{id}")
	public void remover(Long id){
		Agendamento agendamento = daoAgendamento.carrega(id);
		daoAgendamento.remove(agendamento);
		result.redirectTo(AgendamentosController.class).list();
	}
}
