package br.com.doctors.modelo.consultas;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.administracao.Paciente;

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
	private String data;
	private String hora;
	private String queixaPrincipal;
	private String observacoes;
	
	@ManyToOne(fetch=FetchType.EAGER)	@JoinColumn(name="paciente_id")
 	private Paciente paciente;
	
	@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="medico_id")
	private Medico medico;
	
	@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="convenio_id")
	private Convenio convenio;
	
	@OneToMany(mappedBy="consulta",fetch=FetchType.LAZY)
	private List<Atestado> atestados;
	
	@OneToMany(mappedBy="consulta",fetch=FetchType.LAZY)
	private List<Exame> exames;
	
	@OneToMany(mappedBy="consulta",fetch=FetchType.LAZY)
	private List<Receita> receitas;
	
//	@OneToOne(fetch=FetchType.EAGER)
//	private Agendamento agendamento;
	
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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Convenio getConvenio() {
		return convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}
	

	@Override
	public String toString() {
		return String.format("Consulta para: %s - Médico: %s --> %s - %s", paciente.getNome(),
				medico.getNome(), data, hora);
	}
	
}
