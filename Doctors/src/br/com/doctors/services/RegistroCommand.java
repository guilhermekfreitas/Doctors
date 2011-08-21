package br.com.doctors.services;

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
	
}
