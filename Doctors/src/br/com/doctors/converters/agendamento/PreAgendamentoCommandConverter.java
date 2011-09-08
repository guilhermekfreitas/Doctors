package br.com.doctors.converters.agendamento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.commands.AgendamentoCommand;
import br.com.doctors.commands.PreAgendamentoCommand;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class PreAgendamentoCommandConverter implements AgendaConverter {

	private ParametrosAgendamento parametros;

	public PreAgendamentoCommandConverter(ParametrosAgendamento parametros) {
		this.parametros = parametros;
	}

	public Map<LocalDate, PreAgendamentoCommand> converteHorariosParaMap(List<Agendamento> agendamentos) {

		Map<LocalDate,PreAgendamentoCommand> horarios = new HashMap<LocalDate,PreAgendamentoCommand>();

		for (Agendamento agendamento : agendamentos){
			if (horarios.containsKey(agendamento.getDataAgendamento())){
				addEmDataExistente(horarios, agendamento);
			} else {
				addEmNovaData(horarios, agendamento);
			}
		}
		
		return horarios;
	}

	@SuppressWarnings("unchecked")
	@Override
	/****
	 * Recebe horários ocupados da dataAtual, e converte-os em um objeto do tipo AgendamentoCommand
	 */
	public <T extends AgendamentoCommand> T preencheHorariosDoDia(LocalDate dataAtual, 
															Map<LocalDate, T> horariosOcupados) {

		PreAgendamentoCommand diaDeAgendamentos = new PreAgendamentoCommand(dataAtual, parametros);

//		diaAtual = preencheDiaComTodosHorarios(diaAtual);
//		diaAtual = eliminaHorariosOcupados(dataAtual, horariosOcupados, diaAtual);

		diaDeAgendamentos.preencheComHorarios(dataAtual,horariosOcupados);
		
		return (T) diaDeAgendamentos;
	}

//	private PreAgendamentoCommand preencheDiaComTodosHorarios(PreAgendamentoCommand diaAtual) {
//		LocalTime horarioAtual = getHoraInicioAtendimento();
//		while( !horarioAtendimentoTerminou(horarioAtual)){
//			diaAtual.addHorario(horarioAtual);
//			horarioAtual = proximoHorarioApos(horarioAtual);
//		}
//		return diaAtual;
//	}

//	private <T> PreAgendamentoCommand eliminaHorariosOcupados(LocalDate dataAtual, Map<LocalDate, T> horariosOcupados, 
//																			PreAgendamentoCommand diaAtual) {
//		if (horariosOcupados.containsKey(dataAtual)){
//			PreAgendamentoCommand dataComHorariosOcupados = (PreAgendamentoCommand) horariosOcupados.get(dataAtual);
//			diaAtual.removeHorariosOcupados(dataComHorariosOcupados.getHorarios());
//		}
//		return diaAtual;
//	}

//	private boolean horarioAtendimentoTerminou(LocalTime horarioAtual) {
//		return horarioAtual.isAfter(parametros.getHoraFimAtendimento());
//	}
//
//	private LocalTime proximoHorarioApos(LocalTime horarioAtual) {
//		return new LocalTime(horarioAtual).plus(parametros.getMinutosPorConsulta());
//	}
//
//	private LocalTime getHoraInicioAtendimento() {
//		return new LocalTime(parametros.getHoraInicioAtendimento());
//	}
	
	private void addEmDataExistente(Map<LocalDate, PreAgendamentoCommand> horarios, Agendamento agendamento) {
		PreAgendamentoCommand data = horarios.get(agendamento.getDataAgendamento());
		data.addHorario(agendamento.getHoraAgendamento());
	}

	private void addEmNovaData(Map<LocalDate, PreAgendamentoCommand> horarios, Agendamento agendamento) {
		PreAgendamentoCommand novaData = new PreAgendamentoCommand(agendamento.getDataAgendamento(), parametros);
		novaData.addHorario(agendamento.getHoraAgendamento());
		horarios.put(agendamento.getDataAgendamento(), novaData);
	}

	
}
