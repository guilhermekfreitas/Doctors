package br.com.doctors.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

public class DaoImpl<T> implements Dao<T> {

	private final Session session;
	private final Class classe;
	
	public DaoImpl(Session session, Class classe) {
		this.session = session;
		this.classe = classe;
	}
	
	@Override
	public void adiciona(T t) {
		session.save(t);
	}

	@Override
	public void remove(T t) {
		session.delete(t);
	}

	@Override
	public void atualiza(T t) {
		session.merge(t);
	}

	@Override
	public List<T> listaTudo() {
		return session.createCriteria(classe).list();
	}

	@Override
	public T procura(Serializable id) {
		return (T) session.get(classe, id);
	}

}
