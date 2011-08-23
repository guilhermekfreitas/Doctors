package br.com.doctors.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormatter;

import br.com.doctors.modelo.agendamento.Agendamento;

public class AgendaCommandConverter {

	private final LocalTime inicioAtendimento;
	private final LocalTime fimAtendimento;
	private final Minutes minutosPorConsulta;
	private DateTimeFormatter fmtData;
	private DateTimeFormatter fmtHora;
	
	public AgendaCommandConverter(LocalTime inicioAtendimento, LocalTime fimAtendimento, 
			Minutes minutosPorConsulta, DateTimeFormatter fmtData, DateTimeFormatter fmtHora) {
		this.fmtData = fmtData;
		this.inicioAtendimento = inicioAtendimento;
		this.fimAtendimento = fimAtendimento;
		this.minutosPorConsulta = minutosPorConsulta;
		this.fmtHora = fmtHora;
	}
	
	public Map<LocalDate, AgendaCommand> convertToMap(List<Agendamento> horariosJaPreenchidos) {
		
		Map<LocalDate,AgendaCommand> horarios = new HashMap<LocalDate,AgendaCommand>();
		
		for (Agendamento horario : horariosJaPreenchidos){
			
			LocalDate dataAgendamento = horario.getDataAgendamento();
			LocalTime horaAgendamento = horario.getHoraAgendamento();
			RegistroCommand registro = new RegistroCommand(getHorarioAtendimento(horaAgendamento), horario.getNomePaciente(), horario.getStatus());
			
			if (horarios.containsKey(dataAgendamento)){
				// adiciona mais um hor�rio
				AgendaCommand data = horarios.get(dataAgendamento);
				data.addHorario(registro);
			} else {
				// deve adicionar mais uma data
				AgendaCommand newData = new AgendaCommand(dataAgendamento.toString(fmtData));
				newData.addHorario(registro);
				horarios.put(dataAgendamento, newData);
			}
		}
		return horarios;
	}
	
	public AgendaCommand preencheHorariosDoDia(LocalDate dataAtual, Map<LocalDate, AgendaCommand> horariosOcupados) {
		LocalTime horarioAtual = new LocalTime(inicioAtendimento);

		AgendaCommand diaAtual = new AgendaCommand(dataAtual.toString(fmtData));
		while( !horarioAtual.isAfter(fimAtendimento)){
			RegistroCommand registro = new RegistroCommand(getHorarioAtendimento(horarioAtual), "", "Livre");
			diaAtual.addHorario(registro);
			horarioAtual = new LocalTime(horarioAtual).plus(minutosPorConsulta);
		}
		
		// se tiver esta data no horariosOcupados, preenche com os hor�rios j� ocupados
		if (horariosOcupados.containsKey(dataAtual)){
			AgendaCommand dataComHorariosOcupados = horariosOcupados.get(dataAtual);
			diaAtual.addConsultas(dataComHorariosOcupados.getHorarios());
		}
		
		return diaAtual;
	}
	

	private String getHorarioAtendimento(LocalTime horaAgendamento) {
		String horarioAtendimento = horaAgendamento.toString(fmtHora) + " - " + horaAgendamento.plus(minutosPorConsulta).toString(fmtHora);
		return horarioAtendimento;
	}
}
