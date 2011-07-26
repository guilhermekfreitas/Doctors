package br.com.doctors.dao.consultas;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.administracao.Paciente;
import br.com.doctors.modelo.consultas.Consulta;

@Component
public class ConsultaDao extends DaoImpl<Consulta>{

	public ConsultaDao(Session session) {
		super(session, Consulta.class);
	}

	// pesquisa por médico
	// pesquisa por paciente
	public List<Consulta> buscarConsultas(Medico medico, Paciente paciente) {
		return null;
	}
	
}
