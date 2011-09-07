package br.com.doctors.services;

import br.com.doctors.converters.agendamento.AgendaConverter;
import br.com.doctors.converters.agendamento.PreAgendamentoCommandConverter;
import br.com.doctors.dao.agendamento.AgendamentoDao;

public class AgendamentoForPacienteService extends AgendamentoService {

	public AgendamentoForPacienteService(AgendamentoDao daoAgendamento) {
		super(daoAgendamento);
	}

	@Override
	protected AgendaConverter getAgendaConverter() {
		return new PreAgendamentoCommandConverter(getParametros());
	}

}
