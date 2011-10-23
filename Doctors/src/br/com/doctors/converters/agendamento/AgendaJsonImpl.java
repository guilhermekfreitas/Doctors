package br.com.doctors.converters.agendamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class AgendaJsonImpl<T> {
	private Map<LocalTime,T> mapaConvertidos;
	private ParametrosAgendamento parametros;
	
	public AgendaJsonImpl(ParametrosAgendamento parametros) {
		this.parametros = parametros; 
		mapaConvertidos = new TreeMap<LocalTime,T>();
	}
	
	@SuppressWarnings("unchecked")
	public void adicionaHorarioAgendado(LocalTime horaConsulta, Agendamento agendamento){
		HorarioJsonImpl horarioJson = new HorarioJsonImpl(agendamento, parametros);
		mapaConvertidos.put(horaConsulta, (T) horarioJson );
	}
	
	@SuppressWarnings("unchecked")
	public void adicionaHorarioLivre(LocalDate data, LocalTime horaConsulta){
		HorarioJsonImpl horarioJson = HorarioJsonImpl.criaHorarioLivre(data,horaConsulta, parametros);
		mapaConvertidos.put(horaConsulta, (T) horarioJson );
	}
	
	public List<T> getHorariosJSON(){
		return new ArrayList<T>(mapaConvertidos.values());
	}
}
