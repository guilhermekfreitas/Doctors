package br.com.doctors.dao.administracao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.administracao.Paciente;

/***
 * 
 * @author Bruno
 *
 */
@Component
public class MedicoDao extends DaoImpl<Medico>{

	public MedicoDao(Session session) {
		super(session, Medico.class);
	}
	
	public Medico busca(String nome){
		Criteria criteria = getSession().createCriteria(Medico.class).
				add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		
		return (Medico) criteria.uniqueResult();
	}
}
