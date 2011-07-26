package br.com.doctors.modelo.agendamento;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.doctors.modelo.administracao.Funcionario;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.consultas.Consulta;

// Funcionario: funcionário que realizou a confirmação

/**
 * @author Jonathan/Guilherme
 */

@Entity
@Table(name="agenda")
public class Agendamento {
	@Id @GeneratedValue
	private Long id;
	private String data;
	private String hora;
	private Boolean confirmado = false;
	private Boolean cancelado = false;

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
		this.data = data;
		this.hora = hora;
	}

	@Override
	public String toString() {
		return String.format("Data:%s Hora:%s Paciente:%s Confirmado:%b Cancelado:%b", 
				data, hora, paciente, confirmado, cancelado);
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
	public void setData(String data) {
		this.data = data;
	}

	// uso apenas para JSP popular campos
	public void setHora(String hora) {
		this.hora = hora;
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

}
