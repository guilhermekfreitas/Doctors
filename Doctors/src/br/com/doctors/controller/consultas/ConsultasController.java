package br.com.doctors.controller.consultas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.dao.consultas.ConsultaDao;
import br.com.doctors.dao.consultas.ExameDao;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.consultas.Consulta;
import br.com.doctors.modelo.consultas.Exame;

/**
 * 
 * @author Jonathan/Guilherme
 *
 */
@Resource
public class ConsultasController {
	private ConsultaDao daoConsulta;
	private Result result;
	private Validator validator;
	private AgendamentoDao daoAgendamento;
	private ExameDao exameDao;

	public ConsultasController(ConsultaDao daoConsulta, AgendamentoDao daoAgendamento, Result result, 
				Validator validator, ExameDao exameDao) {
		this.daoConsulta = daoConsulta;
		this.daoAgendamento = daoAgendamento; 
		this.result = result;
		this.validator = validator;
		this.exameDao = exameDao;
	}
	
	@Get @Path({"/consultas"})
	public void list() {
		result.include("consultas", daoConsulta.listaTudo());
	}
	
	@Get @Path("/consultas/novo/{idAgendamento}")
	public void cadastro(Long idAgendamento) {
		result.include("agendamento",daoAgendamento.carrega(idAgendamento));
		result.include("consultas", daoConsulta.listaTudo() );
	}
	
	@Post @Path("/consultas")
	public void adiciona(final Consulta consulta, Collection<String> exames){
		
		System.out.println(consulta.getAgendamento());
		
		System.out.println("======================================");
		//System.out.println("Consulta:" + consulta);
		
		List<Exame> examesList = new ArrayList<Exame>();
		for( String descricao : exames ){
			System.out.println(descricao);
			Exame exame = new Exame(descricao);
			exame.setConsulta(consulta);
			exameDao.adiciona(exame);
			examesList.add(exame);
		}
//		consulta.setExames(examesList);
		
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
	
	public void consulta(){
		
	}
	
	@Path("/consulta/consultarHistorico")
	public void consultarHistorico(Long idPaciente, Long idMedico, 
				LocalDate dataInicial, LocalDate dataFinal){
		
		List<Agendamento> listaAgendamentos;
		if (idMedico == 0){
			// busca p/ todos os medicos
			listaAgendamentos = daoAgendamento.agendamentosPara(idPaciente, dataInicial, dataFinal);
		} else {
		    // busca medico especifico
			listaAgendamentos = daoAgendamento.agendamentosPara(idMedico, idPaciente, dataInicial, dataFinal);
		}
			
			List<Consulta> listaConsultas = new ArrayList<Consulta>();
			for (Agendamento agendamento : listaAgendamentos){
				listaConsultas.add(agendamento.getConsulta());
			}
			
			// falta converter a lista
			
			result.use(Results.json()).from(listaConsultas).serialize();
		
	}
	
	
	
}
