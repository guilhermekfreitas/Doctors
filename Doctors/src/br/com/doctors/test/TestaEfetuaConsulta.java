package br.com.doctors.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.dao.consultas.ConsultaDao;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.consultas.Consulta;
import br.com.doctors.modelo.consultas.Exame;

public class TestaEfetuaConsulta {

	public static void main(String[] args) {
		Session session = SessionUtil.getSession();
		
//		buscaConsulta(session);
		
		deveAdicionar(session);

		
		
		// grava agendamento
	}

	private static void buscaConsulta(Session session) {
		// TODO Auto-generated method stub
		ConsultaDao consultaDao = new ConsultaDao(session);
		Consulta consulta2 = consultaDao.buscaPorAgendamento(93L);
		
		System.out.println("Consulta retornada: " + consulta2);
	}

	public static void deveAdicionar(Session session) {
		session.beginTransaction();
		
		Consulta consulta = new Consulta();
		
		consulta.setQueixaPrincipal("Está com dor de cabeça, e dores no corpo");
		consulta.setObservacoes("Possui alergias: rinite, sinusite");
		
		Exame exame = new Exame("Realizar os seguintes procedimentos: Exame de sangue, ..");
		List<Exame> exames = new ArrayList<Exame>();
		exames.add(exame);
		consulta.setExames(exames);
				
		AgendamentoDao daoAgendamento = new AgendamentoDao(session);
//		Agendamento agendamento = daoAgendamento.carrega(92L);
		Agendamento agendamento = new Agendamento();
		agendamento.setId(94L);
		agendamento = daoAgendamento.carrega(agendamento.getId());
		System.out.println("Agendamento: " + agendamento);
		
		consulta.setAgendamento(agendamento);
		System.out.println("Consulta: " + consulta);
		
		ConsultaDao consultaDao = new ConsultaDao(session);
		consultaDao.adiciona(consulta);
		
		System.out.println("Busca por consulta");
		Consulta consulta2 = consultaDao.buscaPorAgendamento(agendamento.getId());
		
		System.out.println("Consulta retornada: " + consulta2);
		
		agendamento.setConsulta(consulta);
		daoAgendamento.atualiza(agendamento);
		
		session.getTransaction().commit();
	}
}
