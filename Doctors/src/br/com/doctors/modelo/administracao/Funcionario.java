package br.com.doctors.modelo.administracao;

import java.util.List;

import javax.persistence.*;

import br.com.doctors.modelo.agendamento.Agendamento;

@Entity
@Table(name="funcionarios")
public class Funcionario extends Pessoa{
	@Id @GeneratedValue
	private Long id;
	private String dataAdmissao;
	private Integer matricula;
	
	//@OneToMany(mappedBy="funcionario",fetch=FetchType.LAZY)
//	private List<Agendamento> agendamentos;
	
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
	
//	public List<Agendamento> getAgendamentos() {
//		return agendamentos;
//	}
	
	@Override
	public String toString() {
		return getNome() + ": " + matricula;
	}
}
