package br.com.doctors.converters.agendamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class AgendaJson {
	private Map<LocalTime,HorarioJsonImpl> mapaConvertidos;
	private ParametrosAgendamento parametros;
	
	public AgendaJson(ParametrosAgendamento parametros) {
		this.parametros = parametros; 
		mapaConvertidos = new TreeMap<LocalTime,HorarioJsonImpl>();
	}
	
	public void adicionaHorarioAgendado(LocalTime horaConsulta, Agendamento agendamento){
		HorarioJsonImpl horarioJson = new HorarioJsonImpl(agendamento, parametros);
		mapaConvertidos.put(horaConsulta, horarioJson );
	}
	
	public void adicionaHorarioLivre(LocalDate data, LocalTime horaConsulta){
		HorarioJsonImpl horarioJson = HorarioJsonImpl.criaHorarioLivre(data,horaConsulta, parametros);
		mapaConvertidos.put(horaConsulta, horarioJson );
	}
	
	public List<HorarioJsonImpl> getHorariosJSON(){
		return new ArrayList<HorarioJsonImpl>(mapaConvertidos.values());
	}
}
