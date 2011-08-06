package br.com.doctors.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.doctors.modelo.administracao.PerfilUsuario;
import br.com.doctors.modelo.util.TipoPerfil;

public class AdicionaPerfilUsuario {
	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		PerfilUsuario usuario = new PerfilUsuario();
		usuario.setLogin("admin");
		usuario.setSenha("admin");
		usuario.setTipo(TipoPerfil.FUNCIONARIO);
		
		System.out.println("Salvando usuário: " + usuario);
		
		session.beginTransaction();
		session.save(usuario);
		
		session.getTransaction().commit();
		session.close();
	}
}
