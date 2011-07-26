package br.com.doctors.controller.consultas;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.administracao.PacienteDao;
import br.com.doctors.dao.consultas.ConsultaDao;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.consultas.Consulta;

@Resource
public class ConsultasController {
	private ConsultaDao daoConsulta;
	private MedicoDao daoMedico;
	private PacienteDao daoPaciente;
	private Result result;
	private Validator validator;

	public ConsultasController(ConsultaDao daoConsulta, MedicoDao daoMedico, PacienteDao daoPaciente, Result result, Validator validator) {
		this.daoPaciente = daoPaciente;
		this.daoConsulta = daoConsulta;
		this.daoMedico = daoMedico;
		this.result = result;
		this.validator = validator;
	}
	
	@Get @Path({"/consultas"})
	public void list() {
		result.include("consultas", daoConsulta.listaTudo());
	}
	
	@Get @Path("/consultas/novo")
	public void cadastro() {
		result.include("convenios", daoConsulta.listaTudo() );
	}
	
	@Post @Path("/consultas")
	public void adiciona(final Consulta consulta, Long pacienteId, Long medicoId){
		
		Paciente paciente = daoPaciente.carrega(pacienteId);
		System.out.println(paciente);
		
		Medico medico = daoMedico.carrega(medicoId);
		System.out.println(medico);
		
		System.out.println("======================================");
		System.out.println("Consulta:" + consulta);
		
//		validator.checking(new Validations(){{
//			that(consulta.getNome() != null && consulta.getNome().length() >= 3, 
//					"paciente.nome", "nome.obrigatorio");
//			
//			// mais validações
//		}});
//		validator.onErrorUsePageOf(this).cadastro();
		
		daoConsulta.adiciona(consulta);
		result.redirectTo(ConsultasController.class).list();
	}
	
	public ConsultaDao getDaoConsulta() {
		return daoConsulta;
	}

	public void setDaoConsulta(ConsultaDao daoConsulta) {
		this.daoConsulta = daoConsulta;
	}

	public MedicoDao getDaoMedico() {
		return daoMedico;
	}

	public void setDaoMedico(MedicoDao daoMedico) {
		this.daoMedico = daoMedico;
	}

	public PacienteDao getDaoPaciente() {
		return daoPaciente;
	}

	public void setDaoPaciente(PacienteDao daoPaciente) {
		this.daoPaciente = daoPaciente;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Get @Path("/consultas/{id}")
	public Consulta edit(Long id){
		result.include("medicos", daoMedico.listaTudo() );
		return daoConsulta.carrega(id);
	}
	
	@Put @Path("/consultas/{consulta.id}")
	public void alterar(final Consulta consulta){
//		validator.checking(new Validations(){{
//			that(consulta.getNome() != null && consulta.getNome().length() >= 3, 
//					"paciente.nome", "nome.obrigatorio");
//			
//			// mais validações
//		}});
//		validator.onErrorUsePageOf(this).edit(consulta.getId());
		
		daoConsulta.atualiza(consulta);
		result.redirectTo(ConsultasController.class).list();
	}
	
	@Path("/consultas/remover/{id}")
	public void remover(Long id){
		Consulta consulta = daoConsulta.carrega(id);
		daoConsulta.remove(consulta);
		result.redirectTo(ConsultasController.class).list();
	}
}
