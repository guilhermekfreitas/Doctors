package br.com.doctors.dao.administracao;

import java.util.List;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.modelo.administracao.Convenio;
import br.com.doctors.modelo.administracao.Paciente;

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
				.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE)).list();
	}
	
	public List<Convenio> buscaPor(Long idPaciente){
		return session.createCriteria(Convenio.class)
				.createCriteria("pacientes").add(Restrictions.idEq(idPaciente)).
				setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	public List<Convenio> listaTodos() {
		return session.createCriteria(Convenio.class).list();
	}

	public Convenio carrega(Long id) {
		return (Convenio) session.load(Convenio.class, id);
	}
}
