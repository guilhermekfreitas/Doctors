package br.com.doctors.test;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.doctors.dao.administracao.ConvenioDao;
import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.consultas.Consulta;

public class AdicionaPaciente {
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		Paciente p = new Paciente();
		p.setLogin("guilhermekfreitas");
		p.setCpf("06526257976");
		p.setDataDeNascimento(new Date());
		p.setEmail("email@email.com");
		p.setEndereco("endereco");
		
		ConvenioDao dao = new ConvenioDao(session);
		List<Convenio> convenios = dao.listaTudo();
		
		System.out.println(convenios);
		
		/*Consulta consulta = new Consulta();
		consulta.setPaciente(p);
		consulta.setObservacoes("observacao");
		Set<Consulta> consultas = new HashSet<Consulta>();
		consultas.add(consulta);
		
		p.setConsultas(consultas);*/
		
		p.setConvenios(convenios);
		
		session.getTransaction().begin();
		session.save(p);
		session.getTransaction().commit();
	}
}
