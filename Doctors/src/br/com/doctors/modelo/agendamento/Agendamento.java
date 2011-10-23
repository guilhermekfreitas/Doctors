package br.com.doctors.modelo.agendamento;

import java.util.List;

import javax.persistence.Entity;
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
import br.com.doctors.util.json.JSONObject;

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
	private Boolean confirmado = false;
	private Boolean cancelado = false;

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

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public void confirmarPreAgendamento(){
		confirmado = true;
	}
	
	public void cancelarPreAgendamento(){
		cancelado = true;
	}
	
	public void transferirHorario(String data, String hora){
	}

	@Override
	public String toString() {
		return String.format("Data:%s Hora:%s Paciente:%s Confirmado:%b Cancelado:%b", 
				dataAgendamento, horaAgendamento, paciente, confirmado, cancelado);
	}
	
	public Long getId() {
		return id;
	}

	public Boolean getConfirmado() {
		return confirmado;
	}

	public Boolean getCancelado() {
		return cancelado;
	}

	public void setId(Long id) {
		this.id = id;
	}


	// uso apenas para JSP popular campos
	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}

	// uso apenas para JSP popular campos
	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
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
	
	
	public boolean isConsultaDisponivel(){
		return confirmado && !cancelado;
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

	public String getStatus() {
		String status = "ERRO";
		
		if (!confirmado && !cancelado)
			status = "A Confirmar";
		else if (confirmado)
			status = "Confirmado";
		if (cancelado)
			status = "Cancelado";
		return status;
	}

}
