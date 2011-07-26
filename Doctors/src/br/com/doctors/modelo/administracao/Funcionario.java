package br.com.doctors.modelo.administracao;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.doctors.modelo.agendamento.Agendamento;

/**
 * 
 * @author Jonathan/Guilherme
 *
 */

@Entity
@Table(name="funcionarios")
public class Funcionario extends Pessoa{
	@Id @GeneratedValue
	private Long id;
	private String dataAdmissao;
	private Integer matricula;
	
	@OneToMany(mappedBy="funcionario",fetch=FetchType.LAZY)
	private List<Agendamento> agendamentos;
	
	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}
	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}
	public Long getId() {
		return id;
	}
	public String getDataAdmissao() {
		return dataAdmissao;
	}
	public Integer getMatricula() {
		return matricula;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setDataAdmissao(String dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	
	@Override
	public String toString() {
		return getNome() + ": " + matricula;
	}
}
