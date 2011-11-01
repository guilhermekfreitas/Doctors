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

	public Exame(){
	}

	public Exame(String descricao){
		setDescricao(descricao);
	}
	
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

	@Override
	public String getTipo() {
		return "Exame";
	}
}
