package br.com.doctors.services;

import java.util.List;

public class AgendaCommand {
	private String data;
	private List<RegistroCommand> horarios;
	
	public AgendaCommand(String data){
		this.data = data;
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
}
