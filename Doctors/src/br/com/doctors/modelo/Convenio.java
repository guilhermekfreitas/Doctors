package br.com.doctors.modelo;

import javax.persistence.*;


@Entity
public class Convenio {
	@Id
	@GeneratedValue
	private Integer id;
	private String nome;
	private Character tipo;
	
	public Convenio(){
		
	}
	
	public Convenio(String nome, Character tipo){
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public String getNome() {
		return nome;
	}
	public Character getTipo() {
		return tipo;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return nome + " " + tipo;
	}
}
