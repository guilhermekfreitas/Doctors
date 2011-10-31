package br.com.doctors.converters.agendamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class AgendaJsonImpl implements AgendaJson{
	private Map<LocalTime,HorarioJsonImpl> mapaConvertidos;
	private ParametrosAgendamento parametros;
	private LocalDate data;

	public AgendaJsonImpl(ParametrosAgendamento parametros, LocalDate data) {
		this.parametros = parametros;
		this.data = data;
		mapaConvertidos = new TreeMap<LocalTime,HorarioJsonImpl>();
	}
	
	public List<? extends HorarioJson> getHorariosJSON(){
		return new ArrayList<HorarioJsonImpl>(mapaConvertidos.values());
	}

	@Override
	public void addHorario(AgendaDoDia agenda, LocalTime horario) {
		if (agenda.temAgendamentoEm(horario)){
			Agendamento agendamento = agenda.getAgendamento(horario);
			adicionaHorarioAgendado(agendamento);
		} else {
			adicionaHorarioLivre(horario);				
		}
	}

	private void adicionaHorarioAgendado(Agendamento agendamento){
		HorarioJsonImpl horarioJson = new HorarioJsonImpl(agendamento, parametros);
		mapaConvertidos.put(agendamento.getHoraAgendamento(), horarioJson );
	}

	private void adicionaHorarioLivre(LocalTime horaConsulta){
		HorarioJsonImpl horarioJson = HorarioJsonImpl.criaHorarioLivre(data,horaConsulta, parametros);
		mapaConvertidos.put(horaConsulta, horarioJson );
	}

	
}
