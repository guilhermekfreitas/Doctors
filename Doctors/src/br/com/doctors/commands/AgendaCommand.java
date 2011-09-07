package br.com.doctors.commands;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;


public class AgendaCommand implements AgendamentoCommand {
	private String data;
	private List<RegistroCommand> horarios;
	
	public AgendaCommand(LocalDate data, DateTimeFormatter formatterData){
		this.data = data.toString(formatterData);
		horarios = new ArrayList<RegistroCommand>();
	}
	
	public String getData() {
		return data;
	}
	public List<RegistroCommand> getHorarios() {
		return horarios;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public void setHorarios(List<RegistroCommand> horarios) {
		this.horarios = horarios;
	}
	
	public void addHorario(RegistroCommand horario){
		horarios.add(horario);
	}
	
	public void addConsultas(List<RegistroCommand> horariosDisponiveis){
		
		for (RegistroCommand horarioAtual : horariosDisponiveis){
			RegistroCommand registro = getHorario(horarioAtual);
			registro.setNomePaciente(horarioAtual.getNomePaciente());
			registro.setStatus(horarioAtual.getStatus());
		}
	}

	private RegistroCommand getHorario(RegistroCommand horario) {
		return horarios.get(horarios.indexOf(horario));
	}
	
}
