package br.com.doctors.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.administracao.PacienteDao;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.consultas.Consulta;

public class ConsultaTest {
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		PacienteDao daoPaciente = new PacienteDao(session);
		Paciente paciente = daoPaciente.busca("Guilherme Kamizake de Freitas");
		System.out.println(paciente);
		
		MedicoDao daoMedico = new MedicoDao(session);
		Medico medico = daoMedico.busca("João Cleber");
		System.out.println(medico);
		
		Consulta consulta = new Consulta();
//		consulta.setData("10/06/2010");
//		consulta.setHora("08:50");
		consulta.setObservacoes("nada a declarar");
		consulta.setQueixaPrincipal("DOr de estomago");

		session.beginTransaction();
		session.save(consulta);
		session.getTransaction().commit();
		session.close();
		
	}
	
}
