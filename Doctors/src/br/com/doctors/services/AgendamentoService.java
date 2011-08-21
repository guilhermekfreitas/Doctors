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

	public List<DataInner> getHorariosDisponiveis(List<Agendamento> horariosJaPreenchidos){

		Map<LocalDate, DataInner> horariosOcupados = convertToDataInner(horariosJaPreenchidos);
		
		List<DataInner> todosHorarios = new ArrayList<DataInner>();
		
		LocalDate dataAtual = new LocalDate(dataInicial);
		while (!dataAtual.isAfter(dataFinal)){
			
			// preenche os horários para esta data.     
			DataInner horariosDoDia = getHorariosDoDia(dataAtual);
			
			// se tiver esta data no horariosOcupados, elimina os horarios em comum
			if (horariosOcupados.containsKey(dataAtual)){
				DataInner dataComHorariosOcupados = horariosOcupados.get(dataAtual);
				horariosDoDia.removeHorariosOcupados(dataComHorariosOcupados.getHorarios());
			}
			
			// adiciona novos horários
			todosHorarios.add(horariosDoDia);
			dataAtual = dataAtual.plusDays(1); // vai p/ próximo dia
//			dataAtual = new LocalDate(dataAtual).plusDays(1); // vai p/ próximo dia
		}
		
		// debug
//		for (DataInner horario : todosHorarios ){
//			System.out.println(horario);
//		}
//		
		return todosHorarios;
	}

	/***
	 * 
	 * Converte uma lista de Agendamento para um mapa de DataInner
	 * @param horariosJaPreenchidos
	 * @return
	 */
	private Map<LocalDate, DataInner> convertToDataInner(List<Agendamento> horariosJaPreenchidos) {

		Map<LocalDate,DataInner> horarios = new HashMap<LocalDate,DataInner>();
		
		for (Agendamento agend : horariosJaPreenchidos){
			
			LocalDate dataAgendamento = agend.getDataAgendamento();
			LocalTime horaAgendamento = agend.getHoraAgendamento();
			
			if (horarios.containsKey(dataAgendamento)){
				// adiciona mais um horário
				DataInner data = horarios.get(dataAgendamento);
				data.addHorario(horaAgendamento.toString(fmtHora));
			} else {
				// deve adicionar mais uma data
				DataInner newData = new DataInner(dataAgendamento.toString(fmtData));
				newData.addHorario(horaAgendamento.toString(fmtHora));
				horarios.put(dataAgendamento, newData);
			}
		}
		return horarios;
	}

	private DataInner getHorariosDoDia(LocalDate dataAtual) {
		
		LocalTime horarioAtual = new LocalTime(inicioAtendimento);

		DataInner diaAtual = new DataInner(dataAtual.toString(fmtData));
		while( !horarioAtual.isAfter(fimAtendimento)){
			diaAtual.addHorario(horarioAtual.toString(fmtHora));
			horarioAtual = new LocalTime(horarioAtual).plus(minutosPorConsulta);
		}
		
		return diaAtual;
	}
}
