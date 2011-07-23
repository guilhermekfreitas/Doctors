package br.com.doctors.dao;

import java.util.List;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.modelo.Convenio;

@Component
public class ConvenioDao {

	private Session session;

	public ConvenioDao(Session session) {
		this.session = session;
	}

	public void adiciona(Convenio convenio) {
		session.save(convenio);
	}

	public void atualiza(Convenio convenio) {
		session.update(convenio);
	}

	public void remove(Convenio convenio) {
		session.delete(convenio);
	}

	public Convenio get(Long id) {
		return (Convenio) session.get(Convenio.class, id);
	}

	public List<Convenio> busca(String nome) {
		return session.createCriteria(Convenio.class)
				.add(Restrictions.ilike("nome", nome)).list();
	}

	public List<Convenio> listaTodos() {
		return session.createCriteria(Convenio.class).list();
	}

	public Convenio carrega(Long id) {
		return (Convenio) session.load(Convenio.class, id);
	}
}
