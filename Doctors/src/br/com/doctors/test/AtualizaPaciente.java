package br.com.doctors.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.doctors.dao.administracao.ConvenioDao;
import br.com.doctors.dao.administracao.PacienteDao;
import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Paciente;

public class AtualizaPaciente {
	public static void main(String[] args) {
		

		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		PacienteDao dao = new PacienteDao(session);
		Paciente p = dao.carrega(16L);
		
		ConvenioDao daoConv = new ConvenioDao(session);
		List<Convenio> conv = daoConv.busca("Cassi");
		
		System.out.println("Adicionando " + conv);
		
		System.out.println(p);
		System.out.println(p.getConvenios());
		
		p.getConvenios().addAll(conv);
		
//		System.out.println("Removendo " + p.getConvenios().get(0));
//		p.getConvenios().remove(0);
		
		session.beginTransaction();
		session.update(p);
		session.getTransaction().commit();
		session.close();
	}
}	
