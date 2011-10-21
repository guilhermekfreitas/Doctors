package br.com.doctors.dao.util;

import org.hibernate.Session;

import br.com.doctors.modelo.util.ModeloAtestado;

public class DaoModeloAtestado extends DaoImpl<ModeloAtestado> {

	public DaoModeloAtestado(Session session) {
		super(session, ModeloAtestado.class);
		// TODO Auto-generated constructor stub
	}

}
