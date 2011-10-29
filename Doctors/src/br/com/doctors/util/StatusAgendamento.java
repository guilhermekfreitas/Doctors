package br.com.doctors.util;

public enum StatusAgendamento {
	CONFIRMADO("Confirmado"), 
	CANCELADO("Cancelado"), 
	A_CONFIRMAR("A Confirmar"),
	PACIENTE_AUSENTE("Paciente Ausente"),
	FINALIZADO("Finalizado");
	
	private String status;
	
	private StatusAgendamento(String status){
		this.status = status;
	}
	
	@Override
	public String toString() {
		return status;
	}

}
