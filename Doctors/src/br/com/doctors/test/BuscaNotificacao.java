package br.com.doctors.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.util.NotificacaoDao;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.util.Notificacao;

public class BuscaNotificacao {
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration();
		configuration.configure();

		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();

		Medico medico = new MedicoDao(session).busca("Fabio de Melo");
		
		List<Notificacao> notificacoes = new NotificacaoDao(session).buscaPorMedico(medico.getId());
		
		System.out.println("Busca notificações para médico:" + medico);
		
		for (Notificacao notificacao : notificacoes ){
			System.out.println(notificacao);
		}
		
	}
}
