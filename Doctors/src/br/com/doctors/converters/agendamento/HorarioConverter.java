package br.com.doctors.converters.agendamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class HorarioConverter {

	private ParametrosAgendamento parametros;
	private AgendamentoDao daoAgendamento;
	private LocalTime horarioInicial;
	private LocalTime horarioFinal;

	public HorarioConverter(ParametrosAgendamento parametros, AgendamentoDao daoAgendamento) {
		this.parametros = parametros;
		this.daoAgendamento = daoAgendamento;
		this.horarioInicial = parametros.getHoraInicioAtendimento();
		this.horarioFinal = parametros.getHoraFimAtendimento();
	}
	
	
	public List<HorarioJsonImpl> getAgenda(Long idMedico, LocalDate data){
		
		List<Agendamento> listAgendamentos = getAgendamentos(idMedico, data);
		
		AgendaDoDia agenda = new AgendaDoDia(listAgendamentos);

		AgendaJson agendaJson = new AgendaJson(parametros);
			
		LocalTime horaAtual = getHoraInicioExpediente();
		while(estiverNoExpediente(horaAtual)){
			
			if (agenda.temAgendamentoEm(horaAtual)){
				Agendamento agendamento = agenda.getAgendamento(horaAtual);
				agendaJson.adicionaHorarioAgendado(horaAtual, agendamento);
			} else {
				agendaJson.adicionaHorarioLivre(data, horaAtual);				
			}
			
			horaAtual = proximoHorario(horaAtual);
		}
		return agendaJson.getHorariosJSON();
	}


	private List<Agendamento> getAgendamentos(Long idMedico, LocalDate data) {
		return daoAgendamento.agendamentosPara(idMedico, data);
	}
	
	public List<HorarioJsonSimples> getHorariosLivres(Long idMedico, LocalDate data){
		
		LocalTime horaAtual = getHoraInicioExpediente();
		
		List<Agendamento> listAgendamentos = getAgendamentos(idMedico, data);
		
		AgendaDoDia agenda = new AgendaDoDia(listAgendamentos);
		Long contador = 1L;
		
		Map<LocalTime,HorarioJsonSimples> mapaHorariosLivres = new TreeMap<LocalTime,HorarioJsonSimples>();
		while(estiverNoExpediente(horaAtual)){
			
			HorarioJsonSimples horarioJson = null;
			if (!agenda.temAgendamentoEm(horaAtual)){
				horarioJson = criaHorarioJsonSimplesLivre(contador++,data,horaAtual);
				System.out.println(horarioJson);
				mapaHorariosLivres.put(horaAtual, horarioJson );
			}
			
			horaAtual = proximoHorario(horaAtual);
		}
		
		return new ArrayList<HorarioJsonSimples>(mapaHorariosLivres.values());
	}
	
	private HorarioJsonSimples criaHorarioJsonSimplesLivre(Long id, LocalDate data,
			LocalTime horaAtual) {
		return new HorarioJsonSimples(id, data, horaAtual, parametros);
	}


	private LocalTime proximoHorario(LocalTime horaAtual) {
		return parametros.proximaConsultaApos(horaAtual);
	}


	private HorarioJsonImpl criaHorarioJsonImplLivre(LocalDate data, LocalTime horaAtual) {
		return HorarioJsonImpl.criaHorarioLivre(data,horaAtual, parametros);
	}


	private HorarioJsonImpl criaHorarioJson(Agendamento agendamento) {
		return new HorarioJsonImpl(agendamento, parametros);
	}


	private boolean estiverNoExpediente(LocalTime horaAtual) {
		return  !horaAtual.isAfter(horarioFinal);
	}


	private LocalTime getHoraInicioExpediente() {
		return new LocalTime(horarioInicial);
	}
	
}
