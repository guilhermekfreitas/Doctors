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

public class AgendaCommandConverter implements AgendaConverter{

	private final LocalTime horaInicioAtendimento;
	private final LocalTime horaFimAtendimento;
	private final Minutes minutosPorConsulta;
	private DateTimeFormatter fmtData;
	private DateTimeFormatter fmtHora;
	
	public AgendaCommandConverter(LocalTime inicioAtendimento, LocalTime fimAtendimento, 
			Minutes minutosPorConsulta, DateTimeFormatter fmtData, DateTimeFormatter fmtHora) {
		this.horaInicioAtendimento = inicioAtendimento;
		this.horaFimAtendimento = fimAtendimento;
		this.minutosPorConsulta = minutosPorConsulta;
		this.fmtData = fmtData;
		this.fmtHora = fmtHora;
	}
	
	private String getHorarioAtendimento(LocalTime horaAgendamento) {
		String horarioAtendimento = horaAgendamento.toString(fmtHora) + " - " + horaAgendamento.plus(minutosPorConsulta).toString(fmtHora);
		return horarioAtendimento;
	}

	@Override
	public Map<LocalDate, ? extends AgendamentoCommand> converteHorariosParaMap(List<Agendamento> horariosConfirmados) {
		
		Map<LocalDate,AgendaCommand> horarios = new HashMap<LocalDate,AgendaCommand>();
		
		for (Agendamento horario : horariosConfirmados){
			
			LocalDate dataAgendamento = horario.getDataAgendamento();
			LocalTime horaAgendamento = horario.getHoraAgendamento();
			RegistroCommand registro = new RegistroCommand(getHorarioAtendimento(horaAgendamento), horario.getNomePaciente(), horario.getStatus());
			
			if (horarios.containsKey(dataAgendamento)){
				// adiciona mais um horário
				AgendaCommand agendaExistente = horarios.get(dataAgendamento);
				agendaExistente.addHorario(registro);
			} else {
				// deve adicionar mais uma data
				AgendaCommand novaAgenda = new AgendaCommand(dataAgendamento.toString(fmtData));
				novaAgenda.addHorario(registro);
				horarios.put(dataAgendamento, novaAgenda);
			}
		}
		return horarios;
	}

	@Override
	public <T extends AgendamentoCommand> T preencheHorariosDoDia(
			LocalDate dataAtual, Map<LocalDate, T> horariosConfirmados) {
		
		LocalTime horarioAtual = new LocalTime(horaInicioAtendimento);

		AgendaCommand diaAtual = new AgendaCommand(dataAtual.toString(fmtData));
		while( !horarioAtual.isAfter(horaFimAtendimento)){
//			RegistroCommand registro = new RegistroCommand(getHorarioAtendimento(horarioAtual), "", "Livre");
			RegistroCommand registro = RegistroCommand.criaRegistroLivre(getHorarioAtendimento(horarioAtual));
			diaAtual.addHorario(registro);
			horarioAtual = new LocalTime(horarioAtual).plus(minutosPorConsulta);
		}
		
		// se tiver esta data no horariosOcupados, preenche com os horários já ocupados
		if (horariosConfirmados.containsKey(dataAtual)){
			AgendaCommand dataComHorariosOcupados = (AgendaCommand) horariosConfirmados.get(dataAtual);
			diaAtual.addConsultas(dataComHorariosOcupados.getHorarios());
		}
		
		return (T) diaAtual;
	}
}
