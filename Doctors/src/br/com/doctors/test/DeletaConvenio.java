package br.com.doctors.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import br.com.doctors.modelo.administracao.Convenio;

// TESTE
public class DeletaConvenio {
	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		Convenio convenio = new Convenio();
		convenio.setNome("qualquer coisa");
		
		session.beginTransaction();
		Convenio c = (Convenio) session.createCriteria(Convenio.class).add(Restrictions.ilike("nome", "qualquer coisa")).uniqueResult();
		System.out.println(c);
		session.delete(c);
		session.getTransaction().commit();
		
	}
}
