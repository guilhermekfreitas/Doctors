package br.com.doctors.converters.agendamento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormatter;

import br.com.doctors.commands.AgendamentoCommand;
import br.com.doctors.commands.PreAgendamentoCommand;
import br.com.doctors.modelo.agendamento.Agendamento;

public class PreAgendamentoCommandConverter implements AgendaConverter {

	private DateTimeFormatter fmtData;
	private DateTimeFormatter fmtHora;
	private LocalTime inicioAtendimento;
	private LocalTime fimAtendimento;
	private Minutes minutosPorConsulta;

	public PreAgendamentoCommandConverter(LocalTime inicioAtendimento, LocalTime fimAtendimento, 
			Minutes minutosPorConsulta, DateTimeFormatter fmtData, DateTimeFormatter fmtHora) {
		this.fmtData = fmtData;
		this.fmtHora = fmtHora;
		this.inicioAtendimento = inicioAtendimento;
		this.fimAtendimento = fimAtendimento;
		this.minutosPorConsulta = minutosPorConsulta;
	}

	/***
	 * 
	 * Converte uma lista de Agendamento para um mapa de PreAgendamentoCommand
	 * @param horariosJaPreenchidos
	 * @param fmtHora 
	 * @param fmtData 
	 * @return
	 */
	public Map<LocalDate, PreAgendamentoCommand> converteHorariosParaMap(List<Agendamento> horariosJaPreenchidos) {

		Map<LocalDate,PreAgendamentoCommand> horarios = new HashMap<LocalDate,PreAgendamentoCommand>();

		for (Agendamento agend : horariosJaPreenchidos){

			LocalDate dataAgendamento = agend.getDataAgendamento();
			LocalTime horaAgendamento = agend.getHoraAgendamento();

			if (horarios.containsKey(dataAgendamento)){
				// adiciona mais um horário
				PreAgendamentoCommand data = horarios.get(dataAgendamento);
				data.addHorario(horaAgendamento.toString(fmtHora));
			} else {
				// deve adicionar mais uma data
				PreAgendamentoCommand newData = new PreAgendamentoCommand(dataAgendamento.toString(fmtData));
				newData.addHorario(horaAgendamento.toString(fmtHora));
				horarios.put(dataAgendamento, newData);
			}
		}
		return horarios;
	}

	@Override
	public <T extends AgendamentoCommand> T preencheHorariosDoDia(
			LocalDate dataAtual, Map<LocalDate, T> horariosOcupados) {
		LocalTime horarioAtual = new LocalTime(inicioAtendimento);

		PreAgendamentoCommand diaAtual = new PreAgendamentoCommand(dataAtual.toString(fmtData));
		while( !horarioAtual.isAfter(fimAtendimento)){
			diaAtual.addHorario(horarioAtual.toString(fmtHora));
			horarioAtual = new LocalTime(horarioAtual).plus(minutosPorConsulta);
		}
		
		// 	se tiver esta data no horariosOcupados, elimina os horarios em comum
		if (horariosOcupados.containsKey(dataAtual)){
			PreAgendamentoCommand dataComHorariosOcupados = (PreAgendamentoCommand) horariosOcupados.get(dataAtual);
			diaAtual.removeHorariosOcupados(dataComHorariosOcupados.getHorarios());
		}

		return (T) diaAtual;
	}
	
	
	
}
