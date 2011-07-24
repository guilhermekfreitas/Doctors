package br.com.doctors.modelo.consultas;

import javax.persistence.*;

@Entity
@Table(name="exames")
public class Exame {
	@Id @GeneratedValue
	private Long id;
	private String descricao;
	
	public Long getId() {
		return id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
