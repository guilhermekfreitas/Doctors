package br.com.doctors.commands;

public class RegistroCommand {
	private String horario;
	private String nomePaciente;
	private String status;
	
	public RegistroCommand(){
		
	}
	
	public RegistroCommand(String horario, String nomePaciente, String status){
		this.horario = horario;
		this.nomePaciente = nomePaciente;
		this.status = status;
	}
	
	// outros membros (ver detalhes)
	
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
	
}
