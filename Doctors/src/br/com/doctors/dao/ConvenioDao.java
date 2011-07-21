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
	
	public void adiciona(Convenio convenio){
		Transaction tx = null;
		try {
		tx = session.beginTransaction();
		session.save(convenio);
		tx.commit();
		} catch (HibernateException exc ){
			exc.printStackTrace();
			tx.rollback();
		}
	}
	
	public void atualiza(Convenio convenio){
		Transaction tx = session.beginTransaction();
		session.update(convenio);
		tx.commit();
	}
	
	public void remove(Convenio convenio){
		Transaction tx = session.beginTransaction();
		session.delete(convenio);
		tx.commit();
	}
	
	public Convenio get(Integer id){
		return (Convenio) session.get(Convenio.class, id);
	}
	
	public List<Convenio> busca(String nome){
		return session.createCriteria(Convenio.class)
			.add(Restrictions.ilike("nome", nome)).list();
	}
	
	public List<Convenio> listaTodos(){
		return session.createCriteria(Convenio.class).list();
	}
}
