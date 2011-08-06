package br.com.doctors.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.doctors.dao.administracao.PerfilUsuarioDao;
import br.com.doctors.modelo.administracao.PerfilUsuario;
import br.com.doctors.modelo.util.TipoPerfil;

public class CarregaPerfilUsuario {
	public static void main(String[] args) throws Exception {
		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		PerfilUsuario usuario = new PerfilUsuario();
		usuario.setLogin("admin");
		usuario.setSenha("admin");
		
		PerfilUsuarioDao dao = new PerfilUsuarioDao(session);
		usuario = dao.logar(usuario);
		
		System.out.println("Carregou usuário: " + usuario);
		
	}
}
