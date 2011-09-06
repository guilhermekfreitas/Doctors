package br.com.doctors.controller.agendamento;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.common.base.Strings;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
import br.com.doctors.UserSession;
import br.com.doctors.dao.administracao.ConvenioDao;
import br.com.doctors.dao.administracao.FuncionarioDao;
import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.administracao.PacienteDao;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.administracao.PerfilUsuario;
import br.com.doctors.modelo.agendamento.Agendamento;
//import br.com.doctors.services.AgendaCommand;
import br.com.doctors.services.AgendaCommand;
import br.com.doctors.services.AgendamentoCommand;
import br.com.doctors.services.AgendamentoService;
import br.com.doctors.services.PreAgendamentoCommand;
import br.com.doctors.services.RegistroCommand;

/**
 * 
 * @author Guilherme
 *
 */

@Resource
public class AgendamentosController {
	private Result result;
	private Validator validator;
	private AgendamentoDao daoAgendamento;
	private PacienteDao daoPaciente;
	private MedicoDao daoMedico;
	private ConvenioDao daoConvenio;
	private FuncionarioDao daoFuncionario;
	private UserSession userSession;

	public AgendamentosController(AgendamentoDao daoAgendamento, Result result, 
			Validator validator, PacienteDao daoPaciente, MedicoDao daoMedico,
			ConvenioDao daoConvenio, FuncionarioDao daoFuncionario, 
			UserSession userSession) {
		this.daoAgendamento = daoAgendamento;
		this.result = result;
		this.validator = validator;
		this.daoPaciente = daoPaciente;
		this.daoMedico = daoMedico;
		this.daoConvenio = daoConvenio;
		this.daoFuncionario = daoFuncionario;
		this.userSession = userSession;
	}
	
	@Get @Path({"/agenda"})
	public void list() {
		PerfilUsuario usuario = userSession.getUsuario();
		switch (usuario.getTipo()){
		case ROLE_FUNCIONARIO:
			result.include("medicos", daoMedico.listaTudo());
			result.forwardTo(this).agendaDoFuncionario();
			break;
		default:
			result.include("agendamentos", daoAgendamento.listaTudo());
		}
	}
	
	public void agendaDoFuncionario() {
	}
	

	@Get @Path("/agenda/novo")
	public void cadastro() {
		result.include("medicos", daoMedico.listaTudo());
		
		PerfilUsuario usuario = userSession.getUsuario();
		switch(usuario.getTipo()){
			case ROLE_PACIENTE:
				Paciente paciente = daoPaciente.carregaPorPerfil(usuario);
				System.out.println("Pega id:" + paciente.getId());
				result.forwardTo(this).cadastro_paciente(paciente,daoConvenio.buscaPor(paciente.getId()));
				break;
			default:
				result.include("pacientes", daoPaciente.listaTudo());
				break;
		}
	}
	
	@Get @Path("/agenda/novo/{paciente}")
	public void cadastro_paciente(Paciente paciente, List<Convenio> conveniosAssociados){
		result.include("paciente", paciente);
		result.include("convenios", conveniosAssociados);
		
		System.out.println("convenios associados");
		for(Convenio c: conveniosAssociados){
			System.out.println(c);
		}
	}
	
	@Post @Path("/agenda")
	public void adiciona(final Agendamento agendamento){
		
		validator.checking(getValidations(agendamento));
		validator.onErrorForwardTo(this).cadastro();
		
		// verifica se não é particular
		Convenio conv = agendamento.getConvenio(); 
		if (conv != null && conv.getId() != 0){
			Convenio convenio = daoConvenio.carrega(conv.getId());
			agendamento.setConvenio(convenio);
		} else {
			agendamento.setConvenio(null);
		}
		
		daoAgendamento.adiciona(agendamento);
		result.redirectTo(AgendamentosController.class).list();
	}

	private Validations getValidations(final Agendamento agendamento) {
		return new Validations(){{
			that(agendamento.getPaciente() != null && agendamento.getPaciente().getId() != null, 
					"agendamento.paciente.id", "campo.obrigatorio", "Paciente");
			that(agendamento.getMedico() != null && agendamento.getMedico().getId() != null, 
					"agendamento.medico.id", "campo.obrigatorio", "Medico");			
			that(agendamento.getDataAgendamento() != null, 
					"agendamento.dataAgendamento", "campo.obrigatorio", "Data");
			that(agendamento.getHoraAgendamento() != null, 
					"agendamento.horaAgendamento", "campo.obrigatorio", "Hora");
		}};
	}
	
	@Get @Path("/agenda/{id}")
	public Agendamento edit(Long id){
		result.include("funcionarios", daoFuncionario.listaTudo());
		return daoAgendamento.carrega(id);
	}
	
	@Put @Path("/agenda/{agendamento.id}")
	public void alterar(final Agendamento agendamento, Long funcionarioId){
		
//		Funcionario funcionario = daoFuncionario.carrega(funcionarioId);

//		agendamento.setFuncionario(funcionario);
		validator.checking(getValidations(agendamento));
		validator.onErrorForwardTo(this).cadastro();
		
		daoAgendamento.atualiza(agendamento);
		result.redirectTo(AgendamentosController.class).list();
	}
	
	@Path("/agenda/remover/{id}")
	public void remover(Long id){
		Agendamento Agendamento = daoAgendamento.carrega(id);
		daoAgendamento.remove(Agendamento);
		result.redirectTo(AgendamentosController.class).list();
	}
	
	
	@Get
	@Path("/agenda/carregaConvenios/{idPaciente}") 
	public void carregaConvenios(Long idPaciente){
		List<Convenio> lista = daoConvenio.buscaPor(idPaciente);
		result.use(Results.json()).from(lista).serialize();
	}
	
	@Get
	@Path("/agenda/carregaHorarios/{idMedico}")
	public void carregaHorarios(Long idMedico){
		
		// carrega agendamentos entre [amanhã - +2 meses pra frente]
//		List<Agendamento> listAgendamentos = daoAgendamento.carregaPor(idMedico);
		
		AgendamentoService converter = new AgendamentoService(daoAgendamento);
		List<PreAgendamentoCommand> listaHorarios = converter.getHorariosDisponiveis(idMedico);
		
		result.use(Results.json()).from(listaHorarios, "datas").include("horarios").serialize();
	}
	
	@SuppressWarnings("unchecked")
	@Get
	@Path("/agenda/carregaAgenda/{idMedico}")
	public void carregaAgenda(Long idMedico){
		
		// carrega agendamentos entre [hoje - +2 meses pra frente]
//		List<Agendamento> listAgendamentos = daoAgendamento.carregaPor(idMedico);
		
		AgendamentoService converter = new AgendamentoService(daoAgendamento);
		converter.setDataInicial(new LocalDate());
		List<AgendamentoCommand> listaHorarios = (List<AgendamentoCommand>) converter.getAgenda(idMedico);
		
		result.use(Results.json()).from(listaHorarios, "datas").include("horarios").serialize();
	}
	
	
	
}


