package br.com.doctors.dao.administracao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.administracao.PerfilUsuario;
import br.com.doctors.modelo.administracao.UsuarioInvalidoException;

@Component
public class PerfilUsuarioDao extends DaoImpl<PerfilUsuario>{

	public PerfilUsuarioDao(Session session) {
		super(session, PerfilUsuario.class);
	}

	// mudar p/ UsuarioInvalidoException.
	public PerfilUsuario logar(PerfilUsuario usuario) throws UsuarioInvalidoException {
		
		Criteria criteria = getSession().createCriteria(PerfilUsuario.class)
				.add(Restrictions.ilike("login", usuario.getLogin(), MatchMode.EXACT))
				.add(Restrictions.ilike("senha", usuario.getSenha(), MatchMode.EXACT));
		PerfilUsuario user = (PerfilUsuario) criteria.uniqueResult();
		
		if (user == null){
			throw new UsuarioInvalidoException("Usu�rio Inv�lido!");
		}
		return user;
	}
	
	public boolean loginJaExiste(String login){
		return getSession().createCriteria(PerfilUsuario.class)
				.add(Restrictions.ilike("login", login, MatchMode.EXACT)).uniqueResult() != null;
	}
	
	public PerfilUsuario carrega(String nomeUsuario){
		Criteria criteria = getSession().createCriteria(PerfilUsuario.class)
				.add(Restrictions.ilike("login", nomeUsuario, MatchMode.EXACT));
		PerfilUsuario user = (PerfilUsuario) criteria.uniqueResult();
		return user;
	}
}
