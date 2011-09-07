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

		for (Agendamento agendamento : horariosJaPreenchidos){
			if (horarios.containsKey(agendamento.getDataAgendamento())){
				addEmDataExistente(horarios, agendamento);
			} else {
				addEmNovaData(horarios, agendamento);
			}
		}
		
		return horarios;
	}

	private void addEmDataExistente(Map<LocalDate, PreAgendamentoCommand> horarios, Agendamento agendamento) {
		PreAgendamentoCommand data = horarios.get(agendamento.getDataAgendamento());
		data.addHorario(agendamento.getHoraAgendamento());
	}

	private void addEmNovaData(Map<LocalDate, PreAgendamentoCommand> horarios, Agendamento agendamento) {
		PreAgendamentoCommand novaData = new PreAgendamentoCommand(agendamento.getDataAgendamento(), parametros);
		novaData.addHorario(agendamento.getHoraAgendamento());
		horarios.put(agendamento.getDataAgendamento(), novaData);
	}

	@Override
	public <T extends AgendamentoCommand> T preencheHorariosDoDia(
			LocalDate dataAtual, Map<LocalDate, T> horariosOcupados) {
		LocalTime horarioAtual = new LocalTime(parametros.getHoraInicioAtendimento());

		PreAgendamentoCommand diaAtual = new PreAgendamentoCommand(dataAtual, parametros);
		while( !horarioAtual.isAfter(parametros.getHoraFimAtendimento())){
			diaAtual.addHorario(horarioAtual);
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
