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
import br.com.doctors.commands.AgendamentoCommand;
import br.com.doctors.converters.agendamento.AgendaCommandConverter;
import br.com.doctors.converters.agendamento.AgendaConverter;
import br.com.doctors.converters.agendamento.PreAgendamentoCommandConverter;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.agendamento.Agendamento;

@Component
public abstract class AgendamentoService {
	protected final LocalTime inicioAtendimento;
	protected final LocalTime fimAtendimento;
	protected final DateTimeFormatter fmtHora = DateTimeFormat.forPattern("HH:mm");
	protected final DateTimeFormatter fmtData = DateTimeFormat.forPattern("dd/MM/yyyy");
	protected final Minutes minutosPorConsulta;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
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

	public List<? extends AgendamentoCommand> getAgenda(Long idMedico){
		
		// carrega agendamentos entre [amanhã - +2 meses pra frente]
		List<Agendamento> horariosConfirmados = daoAgendamento.carregaPor(idMedico);

		AgendaConverter converter = getAgendaConverter();
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
		//				for (PreAgendamentoCommand horario : todosHorarios ){
		//					System.out.println(horario);
		//				}
		return todosHorarios;
	}

	protected abstract AgendaConverter getAgendaConverter();

}
