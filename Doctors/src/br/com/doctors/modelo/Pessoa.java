package br.com.doctors.modelo;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

public class Pessoa {

	private String nome;
	@OneToMany(mappedBy="convenio", fetch=FetchType.LAZY)
	private Convenio convenio;
	
}
