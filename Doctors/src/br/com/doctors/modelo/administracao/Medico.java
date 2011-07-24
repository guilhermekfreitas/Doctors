package br.com.doctors.modelo.administracao;

import javax.persistence.*;

@Entity
@Table(name="medicos")
public class Medico extends Pessoa {
	@Id @GeneratedValue
	private Long id;
	private Integer crm;
	private String uf;
	private String especialidade;
	
	public Long getId() {
		return id;
	}
	public Integer getCrm() {
		return crm;
	}
	public String getUf() {
		return uf;
	}
	public String getEspecialidade() {
		return especialidade;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCrm(Integer crm) {
		this.crm = crm;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s - %s ", getNome(), crm, especialidade);
	}
}
