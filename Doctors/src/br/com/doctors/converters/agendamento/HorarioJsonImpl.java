package br.com.doctors.converters.agendamento;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;

import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

import com.google.common.base.Strings;

public class HorarioJsonImpl implements HorarioJson {
	private String dataAgendamento;
	private String confirmado;
	private String cancelado;
	private String idConvenio;
	private String nomeConvenio;
	private String horario;
	
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
		atributos.put("nomeFuncionario", verifica((agendamento.getFuncionario()!= null ? agendamento.getFuncionario().getNome() : "Ainda n�o foi confirmado")));
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

	public String getIdAgendamento() {
		return id.toString();
	}

	public String getDataAgendamento() {
		return dataAgendamento;
	}

	public String getConfirmado() {
		return confirmado;
	}

	public String getCancelado() {
		return cancelado;
	}

	public String getIdConvenio() {
		return idConvenio;
	}

	public String getNomeConvenio() {
		return nomeConvenio;
	}

	public void setDataAgendamento(String dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public void setConfirmado(String confirmado) {
		this.confirmado = confirmado;
	}

	public void setCancelado(String cancelado) {
		this.cancelado = cancelado;
	}

	public void setIdConvenio(String idConvenio) {
		this.idConvenio = idConvenio;
	}

	public void setNomeConvenio(String nomeConvenio) {
		this.nomeConvenio = nomeConvenio;
	}

	@Override
	public Map<String,String> getCells() {
		return atributos;
	}

	private String verifica(Object campo) {
		return campo!= null ? campo.toString() : "";
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
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
