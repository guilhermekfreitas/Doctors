package br.com.doctors.services;

import java.util.ArrayList;
import java.util.List;

public class DataInner {
	private String data;
	private List<String> horarios;
	
	public DataInner(String data){
		this.data = data;
		horarios = new ArrayList<String>();
	}
	
	public DataInner(String data, List<String> horarios){
		this.data = data;
		this.horarios = horarios;
	}

	public String getData() {
		return data;
	}
	
	public void addHorario(String horario){
		horarios.add(horario);
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
		if (!(obj instanceof DataInner))
			return false;
		DataInner other = (DataInner) obj;
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