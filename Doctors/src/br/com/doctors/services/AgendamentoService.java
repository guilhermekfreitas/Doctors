package br.com.doctors.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.doctors.modelo.agendamento.Agendamento;

public class AgendamentoService {
	private final LocalTime inicioAtendimento;
	private final LocalTime fimAtendimento;
	private final LocalDate dataInicial;
	private final DateTimeFormatter fmtHora = DateTimeFormat.forPattern("HH:mm");
	private final DateTimeFormatter fmtData = DateTimeFormat.forPattern("dd/MM/yyyy");
	private final LocalDate dataFinal;
	private final Minutes minutosPorConsulta;

	public AgendamentoService() {
		inicioAtendimento = new LocalTime(8, 0);
		fimAtendimento = new LocalTime(17, 30);
		dataInicial = new LocalDate().plusDays(1);
		dataFinal = new LocalDate(dataInicial).plusMonths(2);
		minutosPorConsulta = Minutes.minutes(30);
	}

	public List<DataInner> getHorariosDisponiveis(List<Agendamento> horariosJaPreenchidos){

//		Integer duracaoConsulta = 30;
//		String horaInicioAtendimento = "08:00";
//		String horaFimAtendimento = "18:00";
//		System.out.println(fmtData);
//		System.out.println(fmtHora);

		System.out.println(horariosJaPreenchidos.size());
		Map<LocalDate, DataInner> horariosOcupados = convertToDataInner(horariosJaPreenchidos);
		
		
		
		List<DataInner> todosHorarios = new ArrayList<DataInner>();
		
		LocalDate dataAtual = new LocalDate(dataInicial);
		while (!dataAtual.isAfter(dataFinal)){
			
			// preenche os horários para esta data.             (deixar como variaveis de instancia)
			DataInner horariosDoDia = getHorariosDoDia(dataAtual,minutosPorConsulta);
			
			// se tiver esta data no horariosOcupados, elimina os horarios em comum
			if (horariosOcupados.containsKey(dataAtual)){
				DataInner dataComHorariosOcupados = horariosOcupados.get(dataAtual);
				horariosDoDia.removeHorariosOcupados(dataComHorariosOcupados.getHorarios());
			}
			
			// adiciona novos horários
			todosHorarios.add(horariosDoDia);
			dataAtual = new LocalDate(dataAtual).plusDays(1);
		}
		
		// debug
		for (DataInner horario : todosHorarios ){
			System.out.println(horario);
		}
		
		return todosHorarios;
		
//		for(Agendamento horario : horariosJaPreenchidos ){
//			LocalDate dataAgendamento = horario.getDataAgendamento();
//		}
//		
//		
//		
//		for( LocalTime horario : horariosDiarios ){
//			System.out.println(">>" + horario);
//		}
//			
//		
//		System.out.println("Outra lista de horários: ");
//		List<LocalTime> outraLista = new ArrayList<LocalTime>();
//		outraLista.add(new LocalTime(10,0));
//		outraLista.add(new LocalTime(15,0));
//		outraLista.add(new LocalTime(16,30));
//		
//		System.out.println("Removendo horários não-disponíveis. Resultado:");
//		horariosDiarios.removeAll(outraLista);
//		for( LocalTime horario : horariosDiarios ){
//			System.out.println(">>" + horario);
//		}
//		
//		
//		Collection<DataInner> lista = new ArrayList<DataInner>();
		
		// algo assim
//		lista.removeAll(horariosJaPreenchidos);
		
		// pegar horários preenchidos
		
		// pegar lista com todos os horários
		// tirar os horários já preenchidos.
		
		// retornar lista de horários p/ jsp
		
		
	}

	private Map<LocalDate, DataInner> convertToDataInner(List<Agendamento> horariosJaPreenchidos) {

		// pegar cada horário preenchido, e adicionar no HashMap
		
		Map<LocalDate,DataInner> horariosOcupados = new HashMap<LocalDate,DataInner>();
		
//		List<DataInner> horariosOcupados = new ArrayList<DataInner>();
		for (Agendamento agend : horariosJaPreenchidos){
			
			LocalDate dataAgendamento = agend.getDataAgendamento();
			LocalTime horaAgendamento = agend.getHoraAgendamento();
			
			if (horariosOcupados.containsKey(dataAgendamento)){
				// apenas adiciona mais um horário
				DataInner data = horariosOcupados.get(dataAgendamento);
				data.addHorario(horaAgendamento.toString(fmtHora));
			} else {
				// deve adicionar mais uma data
				DataInner newData = new DataInner(dataAgendamento.toString(fmtData));
				newData.addHorario(horaAgendamento.toString(fmtHora));
				horariosOcupados.put(dataAgendamento, newData);
			}
		}
		
		return horariosOcupados;
	}

	private DataInner getHorariosDoDia(LocalDate dataAtual, Minutes minutosPorConsulta) {
		
		LocalTime horarioAtual = new LocalTime(inicioAtendimento);

		DataInner diaAtual = new DataInner(dataAtual.toString(fmtData));
		while( !horarioAtual.isAfter(fimAtendimento)){
			
			diaAtual.addHorario(horarioAtual.toString(fmtHora));
			horarioAtual = new LocalTime(horarioAtual).plus(minutosPorConsulta);
		}
		
		return diaAtual;
	}
}
