package br.com.doctors.modelo.administracao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="funcionarios")
public class Funcionario extends Pessoa{
	@Id @GeneratedValue
	private Long id;
	private String dataAdmissao;
	private Integer matricula;
	
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
