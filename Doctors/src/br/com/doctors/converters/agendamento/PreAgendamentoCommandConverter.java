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
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class PreAgendamentoCommandConverter implements AgendaConverter {

	private ParametrosAgendamento parametros;

	public PreAgendamentoCommandConverter(ParametrosAgendamento parametros) {
		this.parametros = parametros;
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
				data.addHorario(horaAgendamento.toString(parametros.getHoraFormatter()));
			} else {
				// deve adicionar mais uma data
				PreAgendamentoCommand newData = new PreAgendamentoCommand(dataAgendamento.toString(parametros.getDataFormatter()));
				newData.addHorario(horaAgendamento.toString(parametros.getHoraFormatter()));
				horarios.put(dataAgendamento, newData);
			}
		}
		return horarios;
	}

	@Override
	public <T extends AgendamentoCommand> T preencheHorariosDoDia(
			LocalDate dataAtual, Map<LocalDate, T> horariosOcupados) {
		LocalTime horarioAtual = new LocalTime(parametros.getHoraInicioAtendimento());

		PreAgendamentoCommand diaAtual = new PreAgendamentoCommand(dataAtual.toString(parametros.getDataFormatter()));
		while( !horarioAtual.isAfter(parametros.getHoraFimAtendimento())){
			diaAtual.addHorario(horarioAtual.toString(parametros.getHoraFormatter()));
			horarioAtual = new LocalTime(horarioAtual).plus(parametros.getMinutosPorConsulta());
		}
		
		// 	se tiver esta data no horariosOcupados, elimina os horarios em comum
		if (horariosOcupados.containsKey(dataAtual)){
			PreAgendamentoCommand dataComHorariosOcupados = (PreAgendamentoCommand) horariosOcupados.get(dataAtual);
			diaAtual.removeHorariosOcupados(dataComHorariosOcupados.getHorarios());
		}

		return (T) diaAtual;
	}
	
	
	
}
