package br.com.doctors.modelo.administracao;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.Strings;

import br.com.caelum.vraptor.validator.Validations;
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
	
	public Validations getValidations() {
		return new Validations(){{
			that(Funcionario.this.getNome() != null && Funcionario.this.getNome().length() >= 3, 
					"medico.nome", "nome.obrigatorio");
			that(Funcionario.this.getMatricula() != null, 
					"medico.matricula", "campo.obrigatorio", "número de matrícula");
			that(!Strings.isNullOrEmpty(Funcionario.this.getDataAdmissao()), 
					"medico.dataAdmissao", "campo.obrigatorio", "data de admissão");
			that(!Strings.isNullOrEmpty(Funcionario.this.getLogin() ), 
					"medico.login", "campo.obrigatorio", "Login");
			that(!Strings.isNullOrEmpty(Funcionario.this.getSenha()), 
					"medico.senha", "campo.obrigatorio", "Senha");
		}};
	}
}
