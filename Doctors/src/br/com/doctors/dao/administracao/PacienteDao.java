package br.com.doctors.dao.administracao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.administracao.PerfilUsuario;
import br.com.doctors.modelo.agendamento.Agendamento;

/***
 * 
 * @author Renato
 *
 */
@Component
public class PacienteDao extends DaoImpl<Paciente> {

	public PacienteDao(Session session) {
		super(session, Paciente.class);
	}
	
	public Paciente busca(String nome){
		Criteria criteria = getSession().createCriteria(Paciente.class).
				add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		return (Paciente) criteria.uniqueResult();
	}

	public Paciente carregaPorPerfil(PerfilUsuario usuario) {
		Criteria criteria = getSession().createCriteria(Paciente.class)
				.createCriteria("perfil").add(Restrictions.idEq(usuario.getId()))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Paciente) criteria.uniqueResult();
	}

}
