package br.com.doctors.dao.administracao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.administracao.PerfilUsuario;

@Component
public class PerfilUsuarioDao extends DaoImpl<PerfilUsuario>{

	public PerfilUsuarioDao(Session session) {
		super(session, PerfilUsuario.class);
	}

	// mudar p/ UsuarioInvalidoException.
	public PerfilUsuario logar(PerfilUsuario usuario) throws Exception {
		
		Criteria criteria = getSession().createCriteria(PerfilUsuario.class)
				.add(Restrictions.ilike("login", usuario.getLogin(), MatchMode.EXACT))
				.add(Restrictions.ilike("senha", usuario.getSenha(), MatchMode.EXACT));
		PerfilUsuario user = (PerfilUsuario) criteria.uniqueResult();
		
		if (user == null){
			throw new Exception("Usuário Inválido!");
		}
		return user;
	}
	
}
