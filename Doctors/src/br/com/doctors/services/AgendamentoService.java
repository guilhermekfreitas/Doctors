package br.com.doctors.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTimeFieldType;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.Partial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.modelo.agendamento.Agendamento;

@Component
public class AgendamentoService {
	private final LocalTime inicioAtendimento;
	private final LocalTime fimAtendimento;
	private LocalDate dataInicial;
	private final DateTimeFormatter fmtHora = DateTimeFormat.forPattern("HH:mm");
	private final DateTimeFormatter fmtData = DateTimeFormat.forPattern("dd/MM/yyyy");
	private final LocalDate dataFinal;
	private final Minutes minutosPorConsulta;

	public AgendamentoService() {
		// pode vir parametrizado
		inicioAtendimento = new LocalTime(8, 0);
		fimAtendimento = new LocalTime(17, 30);
		dataInicial = new LocalDate().plusDays(1);
		dataFinal = new LocalDate(dataInicial).plusMonths(2);
		minutosPorConsulta = Minutes.minutes(30);
	}

	public void setDataInicial(LocalDate dataInicial){
		this.dataInicial = dataInicial;
	}
	
	public List<AgendamentoCommand> getHorariosDisponiveis(List<Agendamento> horariosJaPreenchidos){

		Map<LocalDate, AgendamentoCommand> horariosOcupados = convertToDataInner(horariosJaPreenchidos);
		
		List<AgendamentoCommand> todosHorarios = new ArrayList<AgendamentoCommand>();
		
		LocalDate dataAtual = new LocalDate(dataInicial);
		while (!dataAtual.isAfter(dataFinal)){
			
			// preenche os horários para esta data.     
			AgendamentoCommand horariosDoDia = getHorariosDoDia(dataAtual);
			
			// se tiver esta data no horariosOcupados, elimina os horarios em comum
			if (horariosOcupados.containsKey(dataAtual)){
				AgendamentoCommand dataComHorariosOcupados = horariosOcupados.get(dataAtual);
				horariosDoDia.removeHorariosOcupados(dataComHorariosOcupados.getHorarios());
			}
			
			// adiciona novos horários
			todosHorarios.add(horariosDoDia);
			dataAtual = dataAtual.plusDays(1); // vai p/ próximo dia
//			dataAtual = new LocalDate(dataAtual).plusDays(1); // vai p/ próximo dia
		}
		
		// debug
//		for (AgendamentoCommand horario : todosHorarios ){
//			System.out.println(horario);
//		}
//		
		return todosHorarios;
	}

	/***
	 * 
	 * Converte uma lista de Agendamento para um mapa de AgendamentoCommand
	 * @param horariosJaPreenchidos
	 * @return
	 */
	private Map<LocalDate, AgendamentoCommand> convertToDataInner(List<Agendamento> horariosJaPreenchidos) {

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

	private AgendamentoCommand getHorariosDoDia(LocalDate dataAtual) {
		
		LocalTime horarioAtual = new LocalTime(inicioAtendimento);

		AgendamentoCommand diaAtual = new AgendamentoCommand(dataAtual.toString(fmtData));
		while( !horarioAtual.isAfter(fimAtendimento)){
			diaAtual.addHorario(horarioAtual.toString(fmtHora));
			horarioAtual = new LocalTime(horarioAtual).plus(minutosPorConsulta);
		}
		
		return diaAtual;
	}

	////////////////////////////////////////////////////////////////////////////////
	
	/***
	 * 
	 * Pega uma lista de Agendamentos e converte para uma List<AgendaCommand>
	 * @param horariosJaPreenchidos
	 * @return
	 */
	public List<AgendaCommand> getAgenda(List<Agendamento> horariosJaPreenchidos) {
		
		Map<LocalDate, AgendaCommand> horariosOcupados = convertToAgendaCommand(horariosJaPreenchidos);
		
		List<AgendaCommand> todosHorarios = new ArrayList<AgendaCommand>();
		
		LocalDate dataAtual = new LocalDate(dataInicial);
		while (!dataAtual.isAfter(dataFinal)){
			
			// preenche os horários para esta data. (TODOS LIVRES)     
			AgendaCommand horariosDoDia = getAgendaDoDia(dataAtual);
			
			// se tiver esta data no horariosOcupados, preenche com os horários já ocupados
			if (horariosOcupados.containsKey(dataAtual)){
				AgendaCommand dataComHorariosOcupados = horariosOcupados.get(dataAtual);
				horariosDoDia.addConsultas(dataComHorariosOcupados.getHorarios());
			}
			
			// adiciona novos horários
			todosHorarios.add(horariosDoDia);
			dataAtual = dataAtual.plusDays(1); // vai p/ próximo dia
		}
		
		return todosHorarios;
	}

	private AgendaCommand getAgendaDoDia(LocalDate dataAtual) {
		LocalTime horarioAtual = new LocalTime(inicioAtendimento);

		AgendaCommand diaAtual = new AgendaCommand(dataAtual.toString(fmtData));
		while( !horarioAtual.isAfter(fimAtendimento)){
			RegistroCommand registro = new RegistroCommand(getHorarioAtendimento(horarioAtual), "", "Livre");
			diaAtual.addHorario(registro);
			horarioAtual = new LocalTime(horarioAtual).plus(minutosPorConsulta);
		}
		
		return diaAtual;
	}

	private Map<LocalDate, AgendaCommand> convertToAgendaCommand(
			List<Agendamento> horariosJaPreenchidos) {
		
		Map<LocalDate,AgendaCommand> horarios = new HashMap<LocalDate,AgendaCommand>();
		
		for (Agendamento horario : horariosJaPreenchidos){
			
			LocalDate dataAgendamento = horario.getDataAgendamento();
			LocalTime horaAgendamento = horario.getHoraAgendamento();
			RegistroCommand registro = new RegistroCommand(getHorarioAtendimento(horaAgendamento), horario.getNomePaciente(), horario.getStatus());
			
			if (horarios.containsKey(dataAgendamento)){
				// adiciona mais um horário
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

	private String getHorarioAtendimento(LocalTime horaAgendamento) {
		String horarioAtendimento = horaAgendamento.toString(fmtHora) + " - " + horaAgendamento.plus(minutosPorConsulta).toString(fmtHora);
		return horarioAtendimento;
	}
}
