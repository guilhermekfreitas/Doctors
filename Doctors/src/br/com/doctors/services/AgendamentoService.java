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
import br.com.doctors.modelo.util.ParametrosAgendamento;

@Component
public abstract class AgendamentoService {
	private ParametrosAgendamento parametros;
	private AgendamentoDao daoAgendamento;

	public AgendamentoService(AgendamentoDao daoAgendamento) {
		this.parametros = ParametrosAgendamento.parametrosDefault();
		this.daoAgendamento = daoAgendamento;
	}
	
	public AgendamentoService(AgendamentoDao daoAgendamento, ParametrosAgendamento parametros) {
		this.parametros = parametros;
		this.daoAgendamento = daoAgendamento;
	}


	public AgendamentoService comDataInicial(LocalDate dataInicial){
		parametros.setDataInicial(dataInicial);
		return this;
	}

	
	public List<? extends AgendamentoCommand> getAgenda(Long idMedico){
		
		List<Agendamento> horariosConfirmados = daoAgendamento.agendamentosPara(idMedico);

		AgendaConverter converter = getAgendaConverter();
		Map<LocalDate, ? extends AgendamentoCommand> horariosAgrupadosPorDia = converter.converteHorariosParaMap(horariosConfirmados);

		List<AgendamentoCommand> agenda = new ArrayList<AgendamentoCommand>();

		LocalDate dataAtual = new LocalDate(parametros.getDataInicial());
		while (!dataAtual.isAfter(parametros.getDataFinal())){
			AgendamentoCommand registroDoDiaAtual = converter.preencheHorariosDoDia(dataAtual,horariosAgrupadosPorDia);
			agenda.add(registroDoDiaAtual);
			dataAtual = dataAtual.plusDays(1); 
		}
		
		return agenda;
	}

	protected abstract AgendaConverter getAgendaConverter();

	public ParametrosAgendamento getParametros() {
		return parametros;
	}

}
