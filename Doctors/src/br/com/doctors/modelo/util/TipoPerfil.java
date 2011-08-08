package br.com.doctors.modelo.util;

public enum TipoPerfil {
	ROLE_ADMIN("ADMIN"),ROLE_PACIENTE("PACIENTE"),ROLE_FUNCIONARIO("USUARIO"),ROLE_MEDICO("MEDICO");
	
	private String tipo;

	TipoPerfil(String tipo){
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return tipo;
	}
}
