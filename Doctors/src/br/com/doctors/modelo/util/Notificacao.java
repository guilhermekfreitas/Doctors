package br.com.doctors.modelo.util;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.administracao.Funcionario;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.agendamento.Agendamento;

@Entity
@Table(name="notificacoes")
public class Notificacao {
	@Id @GeneratedValue
	private Long id;
	
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime") 
	private DateTime horarioDeNotificacao;
	
	@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="funcionario_id")
	private Funcionario funcionario;
	
	@OneToOne(fetch=FetchType.EAGER) @JoinColumn(name="agendamento_id")
	private Agendamento agendamento;
	
	private Boolean notificado;
	
	public Notificacao(){
		horarioDeNotificacao = new DateTime();
		notificado = false;
	}
	
	public Long getId() {
		return id;
	}
	
	public DateTime getHorarioDeNotificacao() {
		return horarioDeNotificacao;
	}
	
	public LocalTime getHorarioNotificacaoAsLocalTime(){
		return horarioDeNotificacao.toLocalTime();
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public Boolean getNotificado() {
		return notificado;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public void medicoFoiNotificado() {
		this.notificado = true;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}
	
	@Override
	public String toString() {
		return String.format("Notificação de chegada do paciente %s às %s.", 
				agendamento.getPaciente().getNome(), horarioDeNotificacao );
	}

	public void setHorarioDeNotificacao(DateTime horarioDeNotificacao) {
		this.horarioDeNotificacao = horarioDeNotificacao;
	}
	
	public LocalTime getHorarioConsulta(){
		return agendamento.getHoraAgendamento();
	}
	
	public Paciente getPaciente(){
		return agendamento.getPaciente();
	}
}
