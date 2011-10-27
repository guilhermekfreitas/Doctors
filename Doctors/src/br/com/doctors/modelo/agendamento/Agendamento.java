package br.com.doctors.modelo.agendamento;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Funcionario;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.consultas.Consulta;
import br.com.doctors.util.StatusAgendamento;

// Funcionario: funcionário que realizou a confirmação

/**
 * @author Jonathan/Guilherme
 */
@Entity
@Table(name="agenda")
public class Agendamento {
	@Id @GeneratedValue
	private Long id;
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalDate")
	private LocalDate dataAgendamento;
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsString")
	private LocalTime horaAgendamento;
	@Enumerated(EnumType.STRING)
	private StatusAgendamento status;
	
	@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="convenio_id")
	private Convenio convenio;
	
	@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="paciente_id")
	private Paciente paciente;

	@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="medico_id")
	private Medico medico;
	
	@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="funcionario_id")
	private Funcionario funcionario;
	
	@OneToOne(fetch=FetchType.EAGER) @JoinColumn(name="consulta_id")
	private Consulta consulta;

	public Agendamento() {
		status = StatusAgendamento.A_CONFIRMAR;
	}
	
	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public void confirmarPreAgendamento(){
		status = StatusAgendamento.CONFIRMADO;
//		confirmado = true;
	}
	
	public void cancelarPreAgendamento(){
		status = StatusAgendamento.CANCELADO;
//		cancelado = true;
	}
	
	public void transferirHorario(String data, String hora){
	}

	@Override
	public String toString() {
		return String.format("Data:%s Hora:%s Paciente:%s Status:%s", 
				dataAgendamento, horaAgendamento, paciente, status.toString());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Medico getMedico() {
		return medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public Convenio getConvenio() {
		return convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public LocalDate getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(LocalDate dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public LocalTime getHoraAgendamento() {
		return horaAgendamento;
	}

	public void setHoraAgendamento(LocalTime horaAgendamento) {
		this.horaAgendamento = horaAgendamento;
	}

	public String getNomePaciente(){
		return paciente.getNome();
	}

	public void setStatus(StatusAgendamento status) {
		this.status = status;
	}

	public StatusAgendamento getStatus() {
		return status;
	}

}
