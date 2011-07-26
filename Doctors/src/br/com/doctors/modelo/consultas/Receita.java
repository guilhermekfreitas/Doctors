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
 * @author Jonathan/Guilherme
 *
 */

@Entity
@Table(name="receitas")
public class Receita extends Documento {
	
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="consulta_id")
	private Consulta consulta;

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
