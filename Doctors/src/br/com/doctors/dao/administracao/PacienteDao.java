package br.com.doctors.dao.administracao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.administracao.Paciente;

@Component
public class PacienteDao extends DaoImpl<Paciente> {

	public PacienteDao(Session session) {
		super(session, Paciente.class);
	}

}
