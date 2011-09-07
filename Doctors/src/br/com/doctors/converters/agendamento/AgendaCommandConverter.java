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
	
	private String getHorarioAtendimento(LocalTime horaAgendamento) {
		String horarioAtendimento = horaAgendamento.toString(parametros.getHoraFormatter()) + " - " 
										+ horaAgendamento.plus(parametros.getMinutosPorConsulta()).toString(parametros.getHoraFormatter());
		return horarioAtendimento;
	}

	@Override
	public Map<LocalDate, ? extends AgendamentoCommand> converteHorariosParaMap(List<Agendamento> horariosConfirmados) {
		
		Map<LocalDate,AgendaCommand> horarios = new HashMap<LocalDate,AgendaCommand>();
		
		for (Agendamento horario : horariosConfirmados){
			LocalDate dataAgendamento = horario.getDataAgendamento();
			LocalTime horaAgendamento = horario.getHoraAgendamento();
			RegistroCommand registro = new RegistroCommand(getHorarioAtendimento(horaAgendamento), horario.getNomePaciente(), horario.getStatus());
			insereNovoRegistro(horarios, dataAgendamento, registro);
		}
		return horarios;
	}

	private void insereNovoRegistro(Map<LocalDate, AgendaCommand> horarios,
			LocalDate dataAgendamento, RegistroCommand registro) {
		
		if (horarios.containsKey(dataAgendamento)){
			// adiciona mais um horário
			AgendaCommand agendaExistente = horarios.get(dataAgendamento);
			agendaExistente.addHorario(registro);
		} else {
			// deve adicionar mais uma data
			AgendaCommand novaAgenda = new AgendaCommand(dataAgendamento,parametros.getDataFormatter());
			novaAgenda.addHorario(registro);
			horarios.put(dataAgendamento, novaAgenda);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AgendamentoCommand> T preencheHorariosDoDia(
			LocalDate dataAtual, Map<LocalDate, T> horariosConfirmados) {
		
		LocalTime horarioAtual = new LocalTime(parametros.getHoraInicioAtendimento());
		AgendaCommand diaAtual = new AgendaCommand(dataAtual,parametros.getDataFormatter());
		
		while( !horarioAtual.isAfter(parametros.getHoraFimAtendimento())){
			RegistroCommand registro = RegistroCommand.criaRegistroLivre(getHorarioAtendimento(horarioAtual));
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
