package br.com.doctors.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.services.AgendamentoService;

public class TestaAgendamentoService {
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		AgendamentoDao dao = new AgendamentoDao(session);
		List<Agendamento> horariosJaPreenchidos = dao.carregaPor(1L);
		for( Agendamento a : horariosJaPreenchidos ){
			System.out.println(a);
		}
		System.out.println();
		
		AgendamentoService service = new AgendamentoService();
		service.getHorariosDisponiveis(horariosJaPreenchidos );
		
	}
}
