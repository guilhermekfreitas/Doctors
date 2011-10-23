package br.com.doctors.converters.agendamento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalTime;

import br.com.doctors.modelo.agendamento.Agendamento;

public class AgendaDoDia {
	
	private Map<LocalTime,Agendamento> agendamentos;
	
	public AgendaDoDia(List<Agendamento> listAgendamentos) {
		agendamentos = new HashMap<LocalTime,Agendamento>();
		
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
	
}
