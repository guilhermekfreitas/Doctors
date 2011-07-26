package br.com.doctors.modelo.consultas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Jonathan
 *
 */

@Entity
@Table(name="atestados")
public class Atestado extends Documento {

	@Id @GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
