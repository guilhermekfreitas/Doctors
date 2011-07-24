package br.com.doctors.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.doctors.dao.administracao.PacienteDao;
import br.com.doctors.modelo.administracao.Paciente;

public class CarregaPaciente {
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		PacienteDao dao = new PacienteDao(session);
		Paciente p = dao.carrega(16L);
		
		System.out.println(p);
		System.out.println(p.getConvenios());
		
		session.close();
	}
}
