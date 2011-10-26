package br.com.doctors.converters.agendamento;

import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;

import br.com.doctors.modelo.util.ParametrosAgendamento;

public class HorarioJsonSimples implements HorarioJson {
	private Map<String,String> atributos;
	private Long id;
	
	public HorarioJsonSimples(Long id, LocalDate data, LocalTime horaAtual,
			ParametrosAgendamento parametros) {
		
		this.id = id;
		atributos = new LinkedHashMap<String, String>();
		addAtributo("horario", geraHorario(parametros, horaAtual));
	}


	@Override
	public Map<String, String> getCells() {
		return atributos;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	private void addAtributo(String nomeAtributo, String valorAtributo){
		atributos.put(nomeAtributo, valorAtributo);
	}
	
	private String geraHorario(ParametrosAgendamento parametros,
			LocalTime horaAgendamento) {
		
		DateTimeFormatter horaFormatter = parametros.getHoraFormatter();
		
		String horarioInicial = horaAgendamento.toString(horaFormatter);
		String horarioFinal = parametros.proximaConsultaApos(horaAgendamento).toString(horaFormatter);
		String horario = horarioInicial + " - " + horarioFinal;
		return horario;
	}
	
	@Override
	public String toString() {
		return atributos.toString();
	}
	
}
