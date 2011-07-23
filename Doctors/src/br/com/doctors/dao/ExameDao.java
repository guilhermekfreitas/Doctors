package br.com.doctors.dao;

import org.hibernate.Session;

import br.com.doctors.modelo.Exame;

public class ExameDao extends DaoImpl<Exame> {

	public ExameDao(Session session) {
		super(session, Exame.class);
	}

}
