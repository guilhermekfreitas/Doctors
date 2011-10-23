package br.com.doctors.controller.agendamento;

import java.util.List;

import org.joda.time.LocalDate;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
import br.com.doctors.converters.agendamento.HorarioConverter;
import br.com.doctors.converters.agendamento.HorarioJsonImpl;
import br.com.doctors.converters.agendamento.HorarioJsonSimples;
import br.com.doctors.dao.administracao.ConvenioDao;
import br.com.doctors.dao.administracao.FuncionarioDao;
import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.administracao.PacienteDao;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.administracao.PerfilUsuario;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;
import br.com.doctors.util.UserSession;
import br.com.doctors.util.json.JQGridJSONConverter;

/**
 * 
 * @author Guilherme, Jonathan, Renato
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
	private HorarioConverter horarioConverter;

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
		
		ParametrosAgendamento parametros = ParametrosAgendamento.getParametrosDefault();
		horarioConverter = new HorarioConverter(parametros, daoAgendamento);
	}
	
	@Get @Path({"/agenda"})
	public void list() {
		PerfilUsuario usuario = userSession.getUsuario();
		switch (usuario.getTipo()){
		case ROLE_FUNCIONARIO:
			result.include("medicos", daoMedico.listaTudo());
//			result.forwardTo(this).veragenda(); // teste
//			result.forwardTo(this).agendaDoFuncionario();
			result.forwardTo(this).agenda();
			break;
		default:
			result.include("agendamentos", daoAgendamento.listaTudo());
		}
	}
	
	public void agenda() {
		
	}

	public void agendaDoFuncionario() {
	}
	
	public void veragenda(){
		
	}

	@Get @Path("/agenda/novo")
	public void cadastro() {
		result.include("medicos", daoMedico.listaTudo());
		
		PerfilUsuario usuario = userSession.getUsuario();
		switch(usuario.getTipo()){
			case ROLE_PACIENTE:
				Paciente paciente = daoPaciente.carregaPorPerfil(usuario);
				result.forwardTo(this).preAgendar(paciente,daoConvenio.buscaPor(paciente.getId()));
				break;
			default:
				result.include("pacientes", daoPaciente.listaTudo());
				break;
		}
	}
	
	@Get @Path("/agenda/novo/{paciente}")
	public void preAgendar(Paciente paciente, List<Convenio> conveniosAssociados){
		result.include("paciente", paciente);
		result.include("convenios", conveniosAssociados);
		
		for(Convenio c: conveniosAssociados){
			System.out.println(c);
		}
	}
	
	
	
	@Post @Path("/agenda")
	public void adiciona(final Agendamento agendamento){
		
		validator.checking(getValidations(agendamento));
		validator.onErrorForwardTo(this).cadastro();
		
		atribueConvenio(agendamento);
		
		daoAgendamento.adiciona(agendamento);
		result.redirectTo(AgendamentosController.class).list();
	}

	private void atribueConvenio(final Agendamento agendamento) {
		// verifica se não é particular
		Convenio conv = agendamento.getConvenio(); 
		if (conv != null && conv.getId() != null){
			Convenio convenio = daoConvenio.carrega(conv.getId());
			agendamento.setConvenio(convenio);
		} else {
			agendamento.setConvenio(null);
		}
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
			that(agendamento.getConvenio() != null && agendamento.getConvenio().getId() != null
				    , "agendamento.convenio.id", "campo.obrigatorio", "Convenio");
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
	
	@Post
	@Path("/agenda/carregaHorarios")
	public void carregaHorariosLivres(Long idMedico, LocalDate data){
		
//		ParametrosAgendamento parametros = ParametrosAgendamento.getParametrosDefault();
//		HorarioConverter horarioConverter = new HorarioConverter(parametros, daoAgendamento);
		List<HorarioJsonSimples> agenda = horarioConverter.getHorariosLivres(idMedico, data);
		
		JQGridJSONConverter jqgrid = new JQGridJSONConverter();
		
		jqgrid.addJSONObjects(agenda);
		
		result.use(Results.json()).withoutRoot().from(jqgrid).include("rows").include("rows.cells").serialize();
	}
	
	@Post
	@Path("/agenda/carregaAgenda") // nomes do parametros devem ficar iguais
	public void carregaAgenda(Long idMedico, LocalDate data){
		
//		ParametrosAgendamento parametros = ParametrosAgendamento.getParametrosDefault();
//		HorarioConverter horarioConverter = new HorarioConverter(parametros, daoAgendamento);
		List<HorarioJsonImpl> agenda = horarioConverter.getAgenda(idMedico, data);
		
		JQGridJSONConverter jqgrid = new JQGridJSONConverter();
		
		jqgrid.addJSONObjects(agenda);
		
		result.use(Results.json()).withoutRoot().from(jqgrid).include("rows").include("rows.cells").serialize();
	}
	
	@Get
	@Path("/agenda/confirmaAgendamento/{idAgendamento}")
	public void confirmaAgendamento(Long idAgendamento){		
		Agendamento agendamento = daoAgendamento.carrega(idAgendamento);
		agendamento.confirmarPreAgendamento();
		daoAgendamento.atualiza(agendamento);		
	}
	
	@Get
	@Path("/agenda/transferirHorario/{idAgendamento}")
	public void transferirHorario(Long idAgendamento, String novaData, String novaHora){
		
		// carrega agendamento
		// alterar data e hora.
		// campo 'confirmado' = false
		
	}
	
	
}


