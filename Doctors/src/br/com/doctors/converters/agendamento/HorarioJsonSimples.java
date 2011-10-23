package br.com.doctors.converters.agendamento;

import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.util.ParametrosAgendamento;

public class HorarioJsonSimples implements HorarioJson {
	private Map<String,String> atributos;
	private Long id;
	
	public HorarioJsonSimples(Long id, LocalDate data, LocalTime horaAtual,
			ParametrosAgendamento parametros) {
		
		this.id = id;
		atributos = new LinkedHashMap<String, String>();
		addAtributo("horario", horaAtual.toString(parametros.getHoraFormatter()));
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
	
	@Override
	public String toString() {
		return atributos.toString();
	}
	
}
