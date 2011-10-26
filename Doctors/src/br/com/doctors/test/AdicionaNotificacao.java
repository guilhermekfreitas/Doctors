package br.com.doctors.test;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.joda.time.DateTime;

import br.com.doctors.dao.administracao.ConvenioDao;
import br.com.doctors.dao.administracao.FuncionarioDao;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Funcionario;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.Notificacao;

public class AdicionaNotificacao {
	public static void main(String[] args) {

		Session session = SessionUtil.getSession();

//		gravaNotificacaoDeHoje(session);
		gravaNotificacaoDeOntem(session);
	}

	private static void gravaNotificacaoDeHoje(Session session) {
		Funcionario funcionario = new FuncionarioDao(session).carrega(1L);
		
		Agendamento agendamento = new AgendamentoDao(session).carrega(69L);
		
		Notificacao notificacao = new Notificacao();
		notificacao.setFuncionario(funcionario);
		notificacao.setAgendamento(agendamento);

		System.out.println("Funcionario: " + funcionario);
		System.out.println("Agendamento: " + agendamento);
		System.out.println("Notificação: " + notificacao );
		
		session.getTransaction().begin();
		session.save(notificacao);
		session.getTransaction().commit();
	}
	
	private static void gravaNotificacaoDeOntem(Session session) {
		Funcionario funcionario = new FuncionarioDao(session).carrega(1L);
		
		Agendamento agendamento = new AgendamentoDao(session).carrega(59L);
		
		Notificacao notificacao = new Notificacao();
		notificacao.setFuncionario(funcionario);
		notificacao.setAgendamento(agendamento);
		notificacao.setHorarioDeNotificacao(new DateTime().minusDays(1));

		System.out.println("Funcionario: " + funcionario);
		System.out.println("Agendamento: " + agendamento);
		System.out.println("Notificação: " + notificacao );
		
		session.getTransaction().begin();
		session.save(notificacao);
		session.getTransaction().commit();
	}
}
