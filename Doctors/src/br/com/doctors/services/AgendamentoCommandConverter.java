package br.com.doctors.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormatter;

import br.com.doctors.modelo.agendamento.Agendamento;

public class AgendamentoCommandConverter {

	private DateTimeFormatter fmtData;
	private DateTimeFormatter fmtHora;
	private LocalTime inicioAtendimento;
	private LocalTime fimAtendimento;
	private Minutes minutosPorConsulta;

	public AgendamentoCommandConverter(LocalTime inicioAtendimento, LocalTime fimAtendimento, 
			Minutes minutosPorConsulta, DateTimeFormatter fmtData, DateTimeFormatter fmtHora) {
		this.fmtData = fmtData;
		this.fmtHora = fmtHora;
		this.inicioAtendimento = inicioAtendimento;
		this.fimAtendimento = fimAtendimento;
		this.minutosPorConsulta = minutosPorConsulta;
	}

	/***
	 * 
	 * Converte uma lista de Agendamento para um mapa de AgendamentoCommand
	 * @param horariosJaPreenchidos
	 * @param fmtHora 
	 * @param fmtData 
	 * @return
	 */
	public Map<LocalDate, AgendamentoCommand> convertToMap(List<Agendamento> horariosJaPreenchidos, 
			DateTimeFormatter fmtData, DateTimeFormatter fmtHora) {

		Map<LocalDate,AgendamentoCommand> horarios = new HashMap<LocalDate,AgendamentoCommand>();

		for (Agendamento agend : horariosJaPreenchidos){

			LocalDate dataAgendamento = agend.getDataAgendamento();
			LocalTime horaAgendamento = agend.getHoraAgendamento();

			if (horarios.containsKey(dataAgendamento)){
				// adiciona mais um horário
				AgendamentoCommand data = horarios.get(dataAgendamento);
				data.addHorario(horaAgendamento.toString(fmtHora));
			} else {
				// deve adicionar mais uma data
				AgendamentoCommand newData = new AgendamentoCommand(dataAgendamento.toString(fmtData));
				newData.addHorario(horaAgendamento.toString(fmtHora));
				horarios.put(dataAgendamento, newData);
			}
		}
		return horarios;
	}

	public AgendamentoCommand preencheHorariosDoDia(LocalDate dataAtual, 
			Map<LocalDate, AgendamentoCommand> horariosOcupados) {

		LocalTime horarioAtual = new LocalTime(inicioAtendimento);

		AgendamentoCommand diaAtual = new AgendamentoCommand(dataAtual.toString(fmtData));
		while( !horarioAtual.isAfter(fimAtendimento)){
			diaAtual.addHorario(horarioAtual.toString(fmtHora));
			horarioAtual = new LocalTime(horarioAtual).plus(minutosPorConsulta);
		}
		
		// 	se tiver esta data no horariosOcupados, elimina os horarios em comum
		if (horariosOcupados.containsKey(dataAtual)){
			AgendamentoCommand dataComHorariosOcupados = horariosOcupados.get(dataAtual);
			diaAtual.removeHorariosOcupados(dataComHorariosOcupados.getHorarios());
		}

		return diaAtual;
	}
	
	
	
}
