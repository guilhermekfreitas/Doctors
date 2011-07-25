package br.com.doctors.dao.administracao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.administracao.Funcionario;

@Component
public class FuncionarioDao extends DaoImpl<Funcionario> {

	public FuncionarioDao(Session session) {
		super(session, Funcionario.class);
	}

}
