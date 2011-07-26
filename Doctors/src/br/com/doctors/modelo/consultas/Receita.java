package br.com.doctors.modelo.consultas;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @author Jonathan/Guilherme
 *
 */

public class Receita extends Documento {
	
	@Id @GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
