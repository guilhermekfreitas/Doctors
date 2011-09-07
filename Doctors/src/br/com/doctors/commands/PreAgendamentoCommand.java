package br.com.doctors.commands;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.util.ParametrosAgendamento;


public class PreAgendamentoCommand implements AgendamentoCommand {
	private String data;
	private List<String> horarios;
	private ParametrosAgendamento parametros;
	
	public PreAgendamentoCommand(LocalDate dataAgendamento, ParametrosAgendamento parametros){
		this.parametros = parametros;
		this.data = dataAgendamento.toString(parametros.getDataFormatter());
		this.horarios = new ArrayList<String>();
	}
	
	public PreAgendamentoCommand(String data, List<String> horarios, ParametrosAgendamento parametros){
		this.data = data;
		this.parametros = parametros;
		this.horarios = horarios;
	}

	public String getData() {
		return data;
	}
	
	public void addHorario(String horario){
		horarios.add(horario);
	}

	public void addHorario(LocalTime horario){
		horarios.add(horario.toString(parametros.getHoraFormatter()));
	}
	
	public List<String> getHorarios() {
		return horarios;
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder("Horários para o dia: " + data + "\n" );
		for (String horario : horarios){
			result.append(horario + " ");
			if (horarios.indexOf(horario) % 5 == 0)
				result.append("\n");
		}
		System.out.println();
		return result.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((horarios == null) ? 0 : horarios.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PreAgendamentoCommand))
			return false;
		PreAgendamentoCommand other = (PreAgendamentoCommand) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (horarios == null) {
			if (other.horarios != null)
				return false;
		} else if (!horarios.equals(other.horarios))
			return false;
		return true;
	}

	public void removeHorariosOcupados(List<String> horariosOcupados) {
		horarios.removeAll(horariosOcupados);
	}
}