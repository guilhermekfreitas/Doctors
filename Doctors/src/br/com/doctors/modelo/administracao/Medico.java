package br.com.doctors.modelo.administracao;

import java.util.List;

import javax.persistence.*;

import br.com.doctors.modelo.consultas.Consulta;

@Entity
@Table(name="medicos")
public class Medico extends Pessoa {
	@Id @GeneratedValue
	private Long id;
	private Integer crm;
	private String uf;
	private String especialidade;
	
	@OneToMany(mappedBy="medico")  //mappedBy: nome do atributo de Consulta que está associado com este
	private List<Consulta> consultas;
	
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
	
	public List<Consulta> getConsultas() {
		return consultas;
	}
	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s - %s ", getNome(), crm, especialidade);
	}
}
