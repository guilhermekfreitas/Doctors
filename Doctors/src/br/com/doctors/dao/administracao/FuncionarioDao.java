package br.com.doctors.dao.administracao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.administracao.Funcionario;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.administracao.PerfilUsuario;

/**
 * 
 * @author Jonathan/Guilherme
 *
 */
@Component
public class FuncionarioDao extends DaoImpl<Funcionario> {

	public FuncionarioDao(Session session) {
		super(session, Funcionario.class);
	}

	public Funcionario buscaPorPerfil(PerfilUsuario usuario) {
		Criteria criteria = getSession().createCriteria(Funcionario.class)
				.createCriteria("perfil").add(Restrictions.idEq(usuario.getId()))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Funcionario) criteria.uniqueResult();
	}
	
}
