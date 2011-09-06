package br.com.doctors.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.agendamento.AgendamentoDao;
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
	private AgendamentoDao daoAgendamento;

	public AgendamentoService(AgendamentoDao daoAgendamento) {
		// pode vir parametrizado
		inicioAtendimento = new LocalTime(8, 0);
		fimAtendimento = new LocalTime(17, 30);
		dataInicial = new LocalDate().plusDays(1);
		dataFinal = new LocalDate(dataInicial).plusMonths(2);
		minutosPorConsulta = Minutes.minutes(30);
		this.daoAgendamento = daoAgendamento;
	}

	public void setDataInicial(LocalDate dataInicial){
		this.dataInicial = dataInicial;
	}
	
	public List<? extends AgendamentoCommand> getHorariosDisponiveis(Long idMedico){
		return getHorarios(idMedico);
	}

	private List<? extends AgendamentoCommand> getHorarios(Long idMedico) {
		// carrega agendamentos entre [amanhã - +2 meses pra frente]
		List<Agendamento> horariosConfirmados = daoAgendamento.carregaPor(idMedico);
		
		AgendaConverter converter = new PreAgendamentoCommandConverter(inicioAtendimento,fimAtendimento,
				minutosPorConsulta,fmtData,fmtHora);
		Map<LocalDate, ? extends AgendamentoCommand> horariosOcupados = converter.convertToMap(horariosConfirmados);
		
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
//		for (PreAgendamentoCommand horario : todosHorarios ){
//			System.out.println(horario);
//		}
//		
		return todosHorarios;
	}

	

	

	////////////////////////////////////////////////////////////////////////////////
	
	/***
	 * 
	 * Pega uma lista de Agendamentos e converte para uma List<AgendaCommand>
	 * @param idMedico
	 * @return
	 */
	public List<? extends AgendamentoCommand> getAgenda(Long idMedico) {
		
		// carrega agendamentos entre [hoje - +2 meses pra frente]
		List<Agendamento> horariosConfirmados = daoAgendamento.carregaPor(idMedico);
		
		AgendaConverter converter = new AgendaCommandConverter(inicioAtendimento, fimAtendimento,
				minutosPorConsulta, fmtData, fmtHora);
		Map<LocalDate, ? extends AgendamentoCommand> horariosOcupados = converter.convertToMap(horariosConfirmados);
		
		List<AgendamentoCommand> todosHorarios = new ArrayList<AgendamentoCommand>();
		
		LocalDate dataAtual = new LocalDate(dataInicial);
		while (!dataAtual.isAfter(dataFinal)){
			
			// preenche os horários para esta data. (TODOS LIVRES)     
			AgendamentoCommand horariosDoDia = converter.preencheHorariosDoDia(dataAtual,horariosOcupados);
			
			// adiciona novos horários
			todosHorarios.add(horariosDoDia);
			dataAtual = dataAtual.plusDays(1); // vai p/ próximo dia
		}
		
		return todosHorarios;
	}

	

}
