package br.com.doctors.modelo.consultas;

import javax.persistence.*;

import br.com.doctors.modelo.administracao.Paciente;

@Entity
@Table(name="consultas")
public class Consulta {
	
	//private Calendar dataHora;
	
	@Id @GeneratedValue
	private Long id;
	private String data;
	private String hora;
	private String queixaPrincipal;
	private String observacoes;
	
	@ManyToOne	@JoinColumn(name="paciente_id")
 	private Paciente paciente;
	
	public void emitirReceita(){ }
	
	public void emitirSolReceita(){ }
	
	public void emitirAtestado(){ }
	
	public Long getId() {
		return id;
	}
	public String getData() {
		return data;
	}
	public String getHora() {
		return hora;
	}
	public String getQueixaPrincipal() {
		return queixaPrincipal;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public void setQueixaPrincipal(String queixaPrincipal) {
		this.queixaPrincipal = queixaPrincipal;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	
}
