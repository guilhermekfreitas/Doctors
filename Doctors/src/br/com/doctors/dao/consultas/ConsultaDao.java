package br.com.doctors.dao.consultas;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.consultas.Consulta;

@Component
public class ConsultaDao extends DaoImpl<Consulta>{

	public ConsultaDao(Session session) {
		super(session, Consulta.class);
	}

	public Consulta buscaPorAgendamento(Long idAgendamento){
		Criteria criteria = getSession().createCriteria(Consulta.class)
				.createCriteria("agendamento").add(Restrictions.idEq(idAgendamento))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Consulta) criteria.uniqueResult();
	}
	
}
