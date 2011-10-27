package br.com.doctors.converters.agendamento;

import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;

import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class HorarioJsonImpl implements HorarioJson {
	private Map<String,String> atributos;
	private Long id;
	
	private HorarioJsonImpl(){
		atributos = new LinkedHashMap<String, String>();
	}
	
	public HorarioJsonImpl( Agendamento agendamento, ParametrosAgendamento parametros) {
		
		this();
		this.id = agendamento.getId();
		
		atributos.put("idAgendamento", verifica(agendamento.getId()) );
		
		LocalDate dataAgendamento = agendamento.getDataAgendamento();
		atributos.put("data", verifica(dataAgendamento.toString(parametros.getDataFormatter())));
		System.out.println(atributos.get("data"));
		
		LocalTime horaAgendamento = agendamento.getHoraAgendamento();
		atributos.put("horario", geraHorario(parametros, horaAgendamento));
		
		atributos.put("idPaciente", verifica(agendamento.getPaciente().getId()));
		atributos.put("nomePaciente", verifica(agendamento.getNomePaciente()));
		atributos.put("status",verifica(agendamento.getStatus()));
		atributos.put("convenio", (agendamento.getConvenio() != null ? agendamento.getConvenio().getNome() : "Particular"));
		atributos.put("idMedico", verifica(agendamento.getMedico().getId()));
		atributos.put("nomeMedico", verifica(agendamento.getMedico().getNome()));
		atributos.put("idFuncionario", verifica((agendamento.getFuncionario()!= null ? agendamento.getFuncionario().getId() : "0")));
		atributos.put("nomeFuncionario", verifica((agendamento.getFuncionario()!= null ? agendamento.getFuncionario().getNome() : "Ainda não foi confirmado")));
	}

	private String geraHorario(ParametrosAgendamento parametros,
			LocalTime horaAgendamento) {
		
		DateTimeFormatter horaFormatter = parametros.getHoraFormatter();
		
		String horarioInicial = horaAgendamento.toString(horaFormatter);
		String horarioFinal = parametros.proximaConsultaApos(horaAgendamento).toString(horaFormatter);
		String horario = horarioInicial + " - " + horarioFinal;
		return horario;
	}

	public Long getId() {
		return id;
	}

	@Override
	public Map<String,String> getCells() {
		return atributos;
	}

	private String verifica(Object campo) {
		return campo!= null ? campo.toString() : "";
	}

	private void addAtributo(String nomeAtributo, String valorAtributo){
		atributos.put(nomeAtributo, valorAtributo);
	}
	
	public static HorarioJsonImpl criaHorarioLivre(LocalDate data, LocalTime horaAtual, ParametrosAgendamento parametros ) {
		HorarioJsonImpl horarioJson = new HorarioJsonImpl();
		
		horarioJson.addAtributo("idAgendamento", "0");
		
		DateTimeFormatter dataFormatter = parametros.getDataFormatter();
		horarioJson.addAtributo("data", data.toString(dataFormatter));
		
		horarioJson.addAtributo("horario", horarioJson.geraHorario(parametros, horaAtual));

		horarioJson.addAtributo("idPaciente", "0");
		horarioJson.addAtributo("nomePaciente", "");
		horarioJson.addAtributo("status", "Livre");
		horarioJson.addAtributo("convenio", "");
		horarioJson.addAtributo("idMedico", "0");
		horarioJson.addAtributo("nomeMedico", "");
		horarioJson.addAtributo("idFuncionario", "0");
		horarioJson.addAtributo("nomeFuncionario", "");
		
		return horarioJson;
	}
	
	@Override
	public String toString() {
		return atributos.toString();
	}
}
