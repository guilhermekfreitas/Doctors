package br.com.doctors.converters.agendamento;

import java.util.List;

import org.joda.time.LocalTime;

public interface AgendaJson {
	public List<? extends HorarioJson> getHorariosJSON();
	public void addHorario(AgendaDoDia agenda, LocalTime horario);
}
