package br.com.doctors.services;

import br.com.doctors.converters.agendamento.AgendaCommandConverter;
import br.com.doctors.converters.agendamento.AgendaConverter;
import br.com.doctors.dao.agendamento.AgendamentoDao;

public class AgendamentoForFuncionarioService extends AgendamentoService {

	public AgendamentoForFuncionarioService(AgendamentoDao daoAgendamento) {
		super(daoAgendamento);
	}

	@Override
	protected AgendaConverter getAgendaConverter() {
		return new AgendaCommandConverter(horaInicioAtendimento, horaFimAtendimento,
					minutosPorConsulta, fmtData, fmtHora);
	}

}
