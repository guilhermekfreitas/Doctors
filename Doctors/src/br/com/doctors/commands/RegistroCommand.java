package br.com.doctors.commands;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class RegistroCommand {
	private String id;
	private LocalDate data;
	private String horario;
	private String nomePaciente;
	private String status;
	private ParametrosAgendamento parametros;
	
	private RegistroCommand(ParametrosAgendamento parametros){
		this.parametros = parametros;
	}
	
	public RegistroCommand(Agendamento horarioDeAgendamento,
			ParametrosAgendamento parametros) {
		this.parametros = parametros;
		this.id = Long.toString(horarioDeAgendamento.getId());
		this.data = horarioDeAgendamento.getDataAgendamento();
		this.horario = formataHorarioAtendimento(horarioDeAgendamento.getHoraAgendamento());
		this.nomePaciente = horarioDeAgendamento.getNomePaciente();
		this.status = horarioDeAgendamento.getStatus();
	}

	public static RegistroCommand criaRegistroLivre(LocalTime horario, ParametrosAgendamento parametros){
		RegistroCommand registro = new RegistroCommand(parametros);
		
		String horarioAsString = horario.toString(parametros.getHoraFormatter()) + " - " 
							+ horario.plus(parametros.getMinutosPorConsulta()).toString(parametros.getHoraFormatter());
		
		registro.setHorario(horarioAsString);
		registro.setStatus("Livre");
		registro.setNomePaciente("");
		registro.setId("0");
		return registro;
	}
	
	
	public String getHorario() {
		return horario;
	}
	public String getNomePaciente() {
		return nomePaciente;
	}
	public String getStatus() {
		return status;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return horario + " -- " + nomePaciente + status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((horario == null) ? 0 : horario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RegistroCommand))
			return false;
		RegistroCommand other = (RegistroCommand) obj;
		if (horario == null) {
			if (other.horario != null)
				return false;
		} else if (!horario.equals(other.horario))
			return false;
		return true;
	}
	
	private String formataHorarioAtendimento(LocalTime horaAgendamento) {
		parametros.getHoraFormatter();
		horaAgendamento.toString();
		String horarioAtendimento = horaAgendamento.toString(parametros.getHoraFormatter()) + " - " 
										+ horaAgendamento.plus(parametros.getMinutosPorConsulta()).toString(parametros.getHoraFormatter());
		return horarioAtendimento;
	}

	public LocalDate getData() {
		return data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
