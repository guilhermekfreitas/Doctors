package br.com.doctors.dao.util;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.Notificacao;

@Component
public class NotificacaoDao extends DaoImpl<Notificacao>{

	public NotificacaoDao(Session session) {
		super(session, Notificacao.class);
	}
	
	public List<Notificacao> buscaPorMedico(Long idMedico){
		
//			.add(Restrictions.like("horarioDeNotificacao", new DateTime()))
		
		Criteria criteria = getSession().createCriteria(Notificacao.class)
			.add(Restrictions.eq("notificado", false))
			.createCriteria("agendamento").createCriteria("medico").add(Restrictions.idEq(idMedico))
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

}
