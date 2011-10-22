package br.com.doctors.modelo.util;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="modelo_atestado")
public class ModeloAtestado {

	@Id @GeneratedValue
	private Long id;	
	private String texto;

	public String getTexto() {
		return texto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}
