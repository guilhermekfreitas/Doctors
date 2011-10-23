package br.com.doctors.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.commands.AgendamentoCommand;
import br.com.doctors.converters.agendamento.AgendaConverter;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

@Component
public abstract class AgendamentoService {
	private ParametrosAgendamento parametros;
	private AgendamentoDao daoAgendamento;

	public AgendamentoService(AgendamentoDao daoAgendamento) {
		this.parametros = ParametrosAgendamento.getParametrosDefault();
		this.daoAgendamento = daoAgendamento;
	}
	
	public AgendamentoService(AgendamentoDao daoAgendamento, ParametrosAgendamento parametros) {
		this.parametros = parametros;
		this.daoAgendamento = daoAgendamento;
	}

	protected abstract AgendaConverter getAgendaConverter();
	
	public List<? extends AgendamentoCommand> getAgenda(Long idMedico, LocalDate dataAgendamentos){
		
		List<Agendamento> horariosConfirmados = daoAgendamento.agendamentosPara(idMedico,dataAgendamentos);
		AgendaConverter converter = getAgendaConverter();
		Map<LocalDate, ? extends AgendamentoCommand> horariosAgrupadosPorDia = converter.agrupaHorariosPorDia(horariosConfirmados);

		List<AgendamentoCommand> agenda = new ArrayList<AgendamentoCommand>();

		LocalDate dataAtual = new LocalDate(parametros.getDataInicial());
		while (!diasDaAgendaTerminou(dataAtual)){
			AgendamentoCommand registroDoDiaAtual = converter.preencheHorariosDoDia(dataAtual,horariosAgrupadosPorDia);
			agenda.add(registroDoDiaAtual);
			dataAtual = dataAtual.plusDays(1); 
		}
		
		return agenda;
	}
	
	private boolean diasDaAgendaTerminou(LocalDate dataAtual) {
		return dataAtual.isAfter(parametros.getDataFinal());
	}

	public AgendamentoService comDataInicial(LocalDate dataInicial){
		parametros.setDataInicial(dataInicial);
		return this;
	}

	public ParametrosAgendamento getParametros() {
		return parametros;
	}

}
