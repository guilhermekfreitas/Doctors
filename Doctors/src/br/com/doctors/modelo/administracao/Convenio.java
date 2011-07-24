package br.com.doctors.modelo.administracao;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

/***
 * 
 * @author <nome do autor>
 *
 */

@Entity
@Table(name="convenios")
public class Convenio {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	
	@ManyToMany(mappedBy="convenios")
	private List<Paciente> pacientes;
	
	public Convenio(){
		
	}
	
	public Convenio(String nome){
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return id+":"+nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
	
}
