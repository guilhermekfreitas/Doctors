package br.com.doctors.converters.notificacao;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;

import br.com.doctors.modelo.util.Notificacao;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class NotificacaoJSon {

	private String idPaciente;
	private String nomePaciente;
	private String nomeFuncionario;
	private String horarioNotificacao;
	private String horarioConsulta;
	
	public NotificacaoJSon(ParametrosAgendamento parametros, Notificacao notificacao) {
		
		this.idPaciente = notificacao.getPaciente().getId().toString();
		this.nomePaciente = notificacao.getPaciente().getNome();
		this.nomeFuncionario = notificacao.getFuncionario().getNome();
		this.horarioNotificacao = converteHorario(notificacao.getHorarioNotificacaoAsLocalTime(),parametros);
		this.horarioConsulta = converteHorario(notificacao.getHorarioConsulta(),parametros);
		
	}
	
	private String converteHorario(LocalTime horario,
			ParametrosAgendamento parametros) {
		DateTimeFormatter horaFormatter = parametros.getHoraFormatter();
		return horario.toString(horaFormatter);
	}

}
