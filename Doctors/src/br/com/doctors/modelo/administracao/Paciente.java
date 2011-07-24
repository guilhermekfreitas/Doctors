package br.com.doctors.modelo.administracao;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import br.com.doctors.modelo.consultas.Consulta;

@Entity
@Table(name="pacientes")
public class Paciente extends Pessoa {
	
	@Id @GeneratedValue
	private Long id;
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	private List<Convenio> convenios;
	
	@OneToMany(mappedBy="paciente",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<Consulta> consultas;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Set<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(Set<Consulta> consultas) {
		this.consultas = consultas;
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
		return getNome() + ": " + getDataDeNascimento();
	}
	
}
