package br.com.doctors.dao.consultas;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.consultas.Exame;

@Component
public class ExameDao extends DaoImpl<Exame> {

	public ExameDao(Session session) {
		super(session, Exame.class);
	}

}
