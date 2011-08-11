package br.com.doctors.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.doctors.dao.administracao.PerfilUsuarioDao;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.administracao.PerfilUsuario;

public class CarregaHorariosMedico {
	public static void main(String[] args) throws Exception {
		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		AgendamentoDao dao = new AgendamentoDao(session);
		dao.carregaPor(1L);
		
	}
}