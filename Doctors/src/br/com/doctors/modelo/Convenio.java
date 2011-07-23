package br.com.doctors.modelo;

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
	
}
