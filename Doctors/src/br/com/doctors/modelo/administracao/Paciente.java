package br.com.doctors.modelo.administracao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.doctors.modelo.agendamento.Agendamento;

@Entity
@Table(name="pacientes")
public class Paciente extends Pessoa {
	
	@Id @GeneratedValue
	private Long id;
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	private List<Convenio> convenios;
	
	@OneToMany(mappedBy="paciente",fetch=FetchType.LAZY)
	private List<Agendamento> agendamentos;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public List<Convenio> getConvenios() {
		return convenios;
	}

	public void setConvenios(List<Convenio> convenios) {
		this.convenios = convenios;
	}
	
	/*public Set<Convenio> getConvenios() {
		return convenios;
	}

	public void adicionaConvenio(Convenio convenio){
		convenios.add(convenio);
	}
	
	public void removeConvenio(Convenio convenio){
		convenios.remove(convenio);
	}*/

	@Override
	public String toString() {
		return getId() + ":" + getNome() + ": " + getDataDeNascimento();
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}
	
}
