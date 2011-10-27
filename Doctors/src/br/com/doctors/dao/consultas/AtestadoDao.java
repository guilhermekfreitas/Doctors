package br.com.doctors.dao.consultas;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.consultas.Atestado;

@Component
public class AtestadoDao extends DaoImpl<Atestado>{

	public AtestadoDao(Session session) {
		super(session, Atestado.class);
	}

}
