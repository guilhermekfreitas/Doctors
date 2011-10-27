package br.com.doctors.converters.consulta;

import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.format.DateTimeFormatter;

import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;
import br.com.doctors.util.json.JSONObject;

public class ConsultaJSon implements JSONObject{

	private Map<String,String> atributos;
	private Long id;
	private transient ParametrosAgendamento parametros;
	
	public ConsultaJSon(Agendamento agendamento, ParametrosAgendamento parametros){
		this.atributos = new LinkedHashMap<String, String>();
		this.id = agendamento.getId();
		this.parametros = parametros;
		
		configuraAtributos(agendamento);
	}
	
	private void configuraAtributos(Agendamento agendamento) {
		DateTimeFormatter dataFormatter = parametros.getDataFormatter();
		addAtributo("data", agendamento.getDataAgendamento().toString(dataFormatter));
		addAtributo("nomeMedico", agendamento.getMedico().getNome());
	}

	private void addAtributo(String nomeAtributo, String valorAtributo) {
		atributos.put(nomeAtributo, valorAtributo);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Map<String, String> getCells() {
		return atributos;
	}
	
	@Override
	public String toString() {
		return atributos.toString();
	}

}
