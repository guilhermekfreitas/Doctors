package br.com.doctors.modelo.administracao;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.caelum.vraptor.validator.Validations;
import br.com.doctors.modelo.agendamento.Agendamento;
import com.google.common.base.Strings;

/***
 * 
 * @author Bruno
 *
 */
@Entity
@Table(name="medicos")
public class Medico extends Pessoa {
	@Id @GeneratedValue
	private Long id;
	private Integer crm;
	private String ufRegistro;
	private String especialidade;
	
//	@OneToMany(mappedBy="medico")  //mappedBy: nome do atributo de Consulta que está associado com este
//	private List<Consulta> consultas;
	
	@OneToMany(mappedBy="medico",fetch=FetchType.LAZY)  //mappedBy: nome do atributo de Agendamento que está associado com este
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
	
	public Validations getValidations() {
		return new Validations(){{
			that(Medico.this.getNome() != null && Medico.this.getNome().length() >= 3, 
					"medico.nome", "nome.obrigatorio");
			that(Medico.this.getCrm() != null, 
					"medico.crm", "campo.obrigatorio", "CRM");
			that(!Strings.isNullOrEmpty(Medico.this.getUfRegistro()), 
					"medico.ufRegistro", "campo.obrigatorio", "relacionado ao UF de registro do médico");
			that(!Strings.isNullOrEmpty(Medico.this.getEspecialidade()), 
					"medico.especialidade", "campo.obrigatorio", "Especialidade");
			that(!Strings.isNullOrEmpty(Medico.this.getLogin() ), 
					"medico.login", "campo.obrigatorio", "Login");
			that(!Strings.isNullOrEmpty(Medico.this.getSenha()), 
					"medico.senha", "campo.obrigatorio", "Senha");
		}};
	}
}
