package br.com.doctors.modelo.consultas;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="consulta_id")
	private Consulta consulta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getTipo() {
		return "Atestado";
	}
	
}
