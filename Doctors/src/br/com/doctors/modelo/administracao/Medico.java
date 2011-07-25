package br.com.doctors.modelo.administracao;

import java.util.List;

import javax.persistence.*;

import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.consultas.Consulta;

@Entity
@Table(name="medicos")
public class Medico extends Pessoa {
	@Id @GeneratedValue
	private Long id;
	private Integer crm;
	private String ufRegistro;
	private String especialidade;
	
//	@OneToMany(mappedBy="medico")  //mappedBy: nome do atributo de Consulta que est� associado com este
//	private List<Consulta> consultas;
	
	@OneToMany(mappedBy="medico",fetch=FetchType.LAZY)  //mappedBy: nome do atributo de Agendamento que est� associado com este
	private List<Agendamento> agendamentos;
	
	public Long getId() {
		return id;
	}
	public Integer getCrm() {
		return crm;
	}
	public String getUfRegistro() {
		return ufRegistro;
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
	public void setUfRegistro(String ufRegistro) {
		this.ufRegistro = ufRegistro;
	}
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	
	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s - %s ", getNome(), crm, especialidade);
	}
}
