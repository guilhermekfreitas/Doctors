package br.com.doctors.dao.agendamento;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.agendamento.Agendamento;

@Component
public class AgendamentoDao extends DaoImpl<Agendamento>{

	public AgendamentoDao(Session session) {
		super(session, Agendamento.class);
	}
	
	public List<Agendamento> carregaPor(Long idMedico){
		LocalDate dataInicial = new LocalDate();
		LocalDate dataFinal = new LocalDate(dataInicial).plusMonths(2);
		Criteria criteria = getSession().createCriteria(Agendamento.class)
			.add(Restrictions.between("dataAgendamento", dataInicial, dataFinal))
			.createCriteria("medico").add(Restrictions.idEq(idMedico))
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

}
