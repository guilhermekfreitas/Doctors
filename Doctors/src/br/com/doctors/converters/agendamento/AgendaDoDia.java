package br.com.doctors.converters.agendamento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.agendamento.Agendamento;

public class AgendaDoDia {
	
	private Map<LocalTime,Agendamento> agendamentos;
	private LocalDate data;
	
	public AgendaDoDia(List<Agendamento> listAgendamentos, LocalDate data) {
		this.agendamentos = new HashMap<LocalTime,Agendamento>();
		this.data = data;
		
		for (Agendamento agendamento : listAgendamentos){
			agendamentos.put(agendamento.getHoraAgendamento(), agendamento);
		}
	}
	
	public boolean temAgendamentoEm(LocalTime horario){
		return agendamentos.containsKey(horario);
	}
	
	public Agendamento getAgendamento(LocalTime horario){
		return agendamentos.get(horario);
	}

	public LocalDate getDataAgendamentos() {
		return data;
	}
	
}
