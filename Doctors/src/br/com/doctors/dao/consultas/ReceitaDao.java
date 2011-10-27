package br.com.doctors.dao.consultas;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.consultas.Receita;

@Component
public class ReceitaDao extends DaoImpl<Receita>{

	public ReceitaDao(Session session) {
		super(session, Receita.class);
	}

}
