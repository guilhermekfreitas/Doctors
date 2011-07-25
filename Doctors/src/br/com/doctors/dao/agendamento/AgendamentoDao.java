package br.com.doctors.dao.agendamento;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.agendamento.Agendamento;

@Component
public class AgendamentoDao extends DaoImpl<Agendamento>{

	public AgendamentoDao(Session session) {
		super(session, Agendamento.class);
	}

}
