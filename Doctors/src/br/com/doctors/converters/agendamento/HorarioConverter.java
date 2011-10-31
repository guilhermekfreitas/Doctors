package br.com.doctors.converters.agendamento;

import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public List<HorarioJsonImpl> getAgenda(Long idMedico, LocalDate data){
		
		List<Agendamento> listAgendamentos = getAgendamentos(idMedico, data);
		AgendaDoDia agenda = new AgendaDoDia(listAgendamentos, data);

		AgendaJson agendaJson = preencheAgendaJson(agenda, new AgendaJsonImpl(parametros, data));
		
		return (List<HorarioJsonImpl>) agendaJson.getHorariosJSON();
	}


	@SuppressWarnings("unchecked")
	public List<HorarioJsonSimples> getHorariosLivres(Long idMedico, LocalDate data){
		
		List<Agendamento> listAgendamentos = getAgendamentos(idMedico, data);
		AgendaDoDia agenda = new AgendaDoDia(listAgendamentos,data);
		
		AgendaJson agendaLivres = preencheAgendaJson(agenda, new AgendaJsonHorariosLivres(parametros, data));
		
		return (List<HorarioJsonSimples>) agendaLivres.getHorariosJSON();
	}


	private AgendaJson preencheAgendaJson(AgendaDoDia agenda, AgendaJson agendaJson) {
		
		LocalTime horaAtual = getHoraInicioExpediente();
		while(estiverNoExpediente(horaAtual)){
			
			if (!estaNoHorarioAlmoco(horaAtual)){
				agendaJson.addHorario(agenda, horaAtual);
			}
			
			horaAtual = proximoHorario(horaAtual);
		}
		return agendaJson;
	}

	private List<Agendamento> getAgendamentos(Long idMedico, LocalDate data) {
		return daoAgendamento.agendamentosPara(idMedico, data);
	}
	
	private LocalTime proximoHorario(LocalTime horaAtual) {
		return parametros.proximaConsultaApos(horaAtual);
	}

	private boolean estiverNoExpediente(LocalTime horaAtual) {
		return  !horaAtual.isAfter(horarioFinal);
	}

	private LocalTime getHoraInicioExpediente() {
		return new LocalTime(horarioInicial);
	}

	private boolean estaNoHorarioAlmoco(LocalTime horaAtual) {
		return parametros.estaNoHorarioAlmoco(horaAtual);
	}

}
