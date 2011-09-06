package br.com.doctors.services;

import java.util.ArrayList;
import java.util.List;

public class AgendaCommand implements AgendamentoCommand {
	private String data;
	private List<RegistroCommand> horarios;
	
	public AgendaCommand(String data){
		this.data = data;
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
		
		for (RegistroCommand horario : horariosDisponiveis){
			RegistroCommand registro = horarios.get(horarios.indexOf(horario));
			registro.setNomePaciente(horario.getNomePaciente());
			registro.setStatus(horario.getStatus());
		}
	}
	
}
