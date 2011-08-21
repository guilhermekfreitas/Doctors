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
	private final LocalDate dataInicial;
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

	public List<AgendamentoCommand> getHorariosDisponiveis(List<Agendamento> horariosJaPreenchidos){

		Map<LocalDate, AgendamentoCommand> horariosOcupados = convertToDataInner(horariosJaPreenchidos);
		
		List<AgendamentoCommand> todosHorarios = new ArrayList<AgendamentoCommand>();
		
		LocalDate dataAtual = new LocalDate(dataInicial);
		while (!dataAtual.isAfter(dataFinal)){
			
			// preenche os hor�rios para esta data.     
			AgendamentoCommand horariosDoDia = getHorariosDoDia(dataAtual);
			
			// se tiver esta data no horariosOcupados, elimina os horarios em comum
			if (horariosOcupados.containsKey(dataAtual)){
				AgendamentoCommand dataComHorariosOcupados = horariosOcupados.get(dataAtual);
				horariosDoDia.removeHorariosOcupados(dataComHorariosOcupados.getHorarios());
			}
			
			// adiciona novos hor�rios
			todosHorarios.add(horariosDoDia);
			dataAtual = dataAtual.plusDays(1); // vai p/ pr�ximo dia
//			dataAtual = new LocalDate(dataAtual).plusDays(1); // vai p/ pr�ximo dia
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
				// adiciona mais um hor�rio
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
	
	public List<AgendaCommand> getAgenda(List<Agendamento> listAgendamentos) {
		return null;
	}
}
