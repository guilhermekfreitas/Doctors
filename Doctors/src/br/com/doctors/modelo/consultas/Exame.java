package br.com.doctors.modelo.consultas;

import javax.persistence.*;

/**
 * 
 * @author Jonathan/Guilherme
 *
 */

@Entity
@Table(name="exames")
public class Exame extends Documento {
	@Id @GeneratedValue
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
