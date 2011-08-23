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

		AgendamentoCommandConverter converter = new AgendamentoCommandConverter(inicioAtendimento,fimAtendimento,
				minutosPorConsulta,fmtData,fmtHora);
		Map<LocalDate, AgendamentoCommand> horariosOcupados = converter.convertToMap(horariosJaPreenchidos,fmtData,fmtHora);
		
		List<AgendamentoCommand> todosHorarios = new ArrayList<AgendamentoCommand>();
		
		LocalDate dataAtual = new LocalDate(dataInicial);
		while (!dataAtual.isAfter(dataFinal)){
			
			// preenche os horários para esta data.     
			AgendamentoCommand horariosDoDia = converter.preencheHorariosDoDia(dataAtual,horariosOcupados);
			
			// adiciona novos horários
			todosHorarios.add(horariosDoDia);
			dataAtual = dataAtual.plusDays(1); // vai p/ próximo dia
		}
		
		// debug
//		for (AgendamentoCommand horario : todosHorarios ){
//			System.out.println(horario);
//		}
//		
		return todosHorarios;
	}

	

	

	////////////////////////////////////////////////////////////////////////////////
	
	/***
	 * 
	 * Pega uma lista de Agendamentos e converte para uma List<AgendaCommand>
	 * @param horariosJaPreenchidos
	 * @return
	 */
	public List<AgendaCommand> getAgenda(List<Agendamento> horariosJaPreenchidos) {
		
		AgendaCommandConverter converter = new AgendaCommandConverter(inicioAtendimento, fimAtendimento,
				minutosPorConsulta, fmtData, fmtHora);
		Map<LocalDate, AgendaCommand> horariosOcupados = converter.convertToMap(horariosJaPreenchidos);
		
		List<AgendaCommand> todosHorarios = new ArrayList<AgendaCommand>();
		
		LocalDate dataAtual = new LocalDate(dataInicial);
		while (!dataAtual.isAfter(dataFinal)){
			
			// preenche os horários para esta data. (TODOS LIVRES)     
			AgendaCommand horariosDoDia = converter.preencheHorariosDoDia(dataAtual,horariosOcupados);
			
			// adiciona novos horários
			todosHorarios.add(horariosDoDia);
			dataAtual = dataAtual.plusDays(1); // vai p/ próximo dia
		}
		
		return todosHorarios;
	}

	

}
