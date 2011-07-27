package br.com.doctors.modelo.consultas;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.persistence.CascadeType;

import br.com.doctors.modelo.agendamento.Agendamento;

/**
 * 
 * @author Jonathan/Guilherme
 *
 */

@Entity
@Table(name="consultas")
public class Consulta {
	
	//private Calendar dataHora;
	
	@Id @GeneratedValue
	private Long id;
	private String data; // retirar
	private String hora;
	private String queixaPrincipal;
	private String observacoes;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy="consulta") @JoinColumn(name="agendamento_id")
	private Agendamento agendamento;
	
	@OneToMany(mappedBy="consulta",fetch=FetchType.LAZY,cascade={CascadeType.PERSIST})
	private List<Atestado> atestados;
	
	@OneToMany(mappedBy="consulta",fetch=FetchType.LAZY)
	private List<Exame> exames;
	
	@OneToMany(mappedBy="consulta",fetch=FetchType.LAZY,cascade={CascadeType.PERSIST})
	private List<Receita> receitas;
	
	public void emitirReceita(){
		
	}
	
	public List<Atestado> getAtestados() {
		return atestados;
	}

	public void setAtestados(List<Atestado> atestados) {
		this.atestados = atestados;
	}

	public List<Exame> getExames() {
		return exames;
	}

	public void setExames(List<Exame> exames) {
		this.exames = exames;
	}

	public List<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}

	public void emitirSolReceita(){
		
	}
	
	public void emitirAtestado(){
		
	}
	
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

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	@Override
	public String toString() {
		return queixaPrincipal;
	}
	
}
