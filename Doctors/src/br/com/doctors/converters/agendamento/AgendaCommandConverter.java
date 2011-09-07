package br.com.doctors.converters.agendamento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormatter;

import br.com.doctors.commands.AgendaCommand;
import br.com.doctors.commands.AgendamentoCommand;
import br.com.doctors.commands.RegistroCommand;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class AgendaCommandConverter implements AgendaConverter{

	private ParametrosAgendamento parametros;
	
	public AgendaCommandConverter(ParametrosAgendamento parametros) {
		this.parametros = parametros;
	}
	
	@Override
	public Map<LocalDate, ? extends AgendamentoCommand> converteHorariosParaMap(List<Agendamento> horariosConfirmados) {
		
		Map<LocalDate,AgendaCommand> horarios = new HashMap<LocalDate,AgendaCommand>();
		
		for (Agendamento horario : horariosConfirmados){
			RegistroCommand registro = new RegistroCommand(horario, parametros);
			insereNovoRegistro(horarios, registro);
		}
		return horarios;
	}

	private void insereNovoRegistro(Map<LocalDate, AgendaCommand> horarios, RegistroCommand registro) {
		
		if (horarios.containsKey(registro.getData())){
			// adiciona mais um horário
			AgendaCommand agendaExistente = horarios.get(registro.getData());
			agendaExistente.addHorario(registro);
		} else {
			// deve adicionar mais uma data
			AgendaCommand novaAgenda = new AgendaCommand(registro.getData(),parametros.getDataFormatter());
			novaAgenda.addHorario(registro);
			horarios.put(registro.getData(), novaAgenda);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AgendamentoCommand> T preencheHorariosDoDia(
			LocalDate dataAtual, Map<LocalDate, T> horariosConfirmados) {
		
		LocalTime horarioAtual = new LocalTime(parametros.getHoraInicioAtendimento());
		AgendaCommand diaAtual = new AgendaCommand(dataAtual,parametros.getDataFormatter());
		
		while( !horarioAtual.isAfter(parametros.getHoraFimAtendimento())){
			RegistroCommand registro = RegistroCommand.criaRegistroLivre(horarioAtual,parametros);
			diaAtual.addHorario(registro);
			horarioAtual = new LocalTime(horarioAtual).plus(parametros.getMinutosPorConsulta());
		}
		
		boolean temHorariosOcupadosParaDataAtual = horariosConfirmados.containsKey(dataAtual);
		if (temHorariosOcupadosParaDataAtual){
			AgendaCommand dataComHorariosOcupados = (AgendaCommand) horariosConfirmados.get(dataAtual);
			diaAtual.addConsultas(dataComHorariosOcupados.getHorarios());
		}
		
		return (T) diaAtual;
	}
	
	
	
}
