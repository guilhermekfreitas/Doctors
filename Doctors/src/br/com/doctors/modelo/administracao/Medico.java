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
import br.com.doctors.modelo.util.TipoPerfil;

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
		return String.format("%s: %s - %s (Login:%s|Senha:%s)", getNome(), crm, especialidade, 
				getPerfil().getLogin(),getPerfil().getSenha());
	}
	
	public Validations getValidations() {
		return null;
	}
	
	@Override
	public void setPerfil(PerfilUsuario perfil) {
		perfil.setTipo(TipoPerfil.ROLE_MEDICO);
		super.setPerfil(perfil);
	}
}
