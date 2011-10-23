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
		AgendaDoDia agenda = new AgendaDoDia(listAgendamentos, data);

		AgendaJson<HorarioJsonImpl> agendaJson = converteParaAgendaJSon(agenda);
		
		return agendaJson.getHorariosJSON();
	}


	private AgendaJson<HorarioJsonImpl> converteParaAgendaJSon(AgendaDoDia agenda ) {
		
		AgendaJson<HorarioJsonImpl> agendaJson = new AgendaJson<HorarioJsonImpl>(parametros);
		
		LocalDate data = agenda.getDataAgendamentos();
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
		return agendaJson;
	}


	private List<Agendamento> getAgendamentos(Long idMedico, LocalDate data) {
		return daoAgendamento.agendamentosPara(idMedico, data);
	}
	
	public List<HorarioJsonSimples> getHorariosLivres(Long idMedico, LocalDate data){
		
		
		List<Agendamento> listAgendamentos = getAgendamentos(idMedico, data);
		
		AgendaDoDia agenda = new AgendaDoDia(listAgendamentos,data);
		Long contador = 1L;
		
		Map<LocalTime,HorarioJsonSimples> mapaHorariosLivres = new TreeMap<LocalTime,HorarioJsonSimples>();

		LocalTime horaAtual = getHoraInicioExpediente();
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


	private boolean estiverNoExpediente(LocalTime horaAtual) {
		return  !horaAtual.isAfter(horarioFinal);
	}


	private LocalTime getHoraInicioExpediente() {
		return new LocalTime(horarioInicial);
	}
	
}
