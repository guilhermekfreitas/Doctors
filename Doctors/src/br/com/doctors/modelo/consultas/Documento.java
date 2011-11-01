package br.com.doctors.modelo.consultas;

import javax.persistence.MappedSuperclass;

/**
 * 
 * @author Jonathan
 *
 */

@MappedSuperclass
public abstract class Documento {
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public abstract String getTipo();

	@Override
	public String toString() {
		return descricao;
	}
}
