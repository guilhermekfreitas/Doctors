package br.com.doctors.dao.administracao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.administracao.Medico;

@Component
public class MedicoDao extends DaoImpl<Medico>{

	public MedicoDao(Session session) {
		super(session, Medico.class);
	}
	

}
