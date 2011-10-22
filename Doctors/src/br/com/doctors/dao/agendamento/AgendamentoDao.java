package br.com.doctors.dao.agendamento;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.dao.util.DaoImpl;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.consultas.Consulta;

@Component
public class AgendamentoDao extends DaoImpl<Agendamento>{

	public AgendamentoDao(Session session) {
		super(session, Agendamento.class);
	}

	/***
	 * Carrega agendamentos entre [amanhã - +2 meses pra frente]
	 * @param idMedico
	 * @return
	 */
	public List<Agendamento> agendamentosPara(Long idMedico){
		LocalDate dataInicial = new LocalDate();
		LocalDate dataFinal = new LocalDate(dataInicial).plusMonths(2);
		Criteria criteria = getSession().createCriteria(Agendamento.class)
				.add(Restrictions.between("dataAgendamento", dataInicial, dataFinal))
				.createCriteria("medico").add(Restrictions.idEq(idMedico))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	// 	pesquisa por médico
	// pesquisa por paciente
	public List<Agendamento> agendamentosPara(Long idMedico, Long idPaciente, 
			LocalDate dataInicial, LocalDate dataFinal) {
		Criteria criteria = getSession().createCriteria(Agendamento.class)
				.add(Restrictions.between("dataAgendamento", dataInicial, dataFinal))
				.createCriteria("medico").add(Restrictions.idEq(idMedico))
				.createCriteria("paciente").add(Restrictions.idEq(idPaciente))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	public List<Agendamento> agendamentosPara(Long idPaciente, LocalDate dataInicial, LocalDate dataFinal) {
		Criteria criteria = getSession().createCriteria(Agendamento.class)
				.add(Restrictions.between("dataAgendamento", dataInicial, dataFinal))
				.createCriteria("paciente").add(Restrictions.idEq(idPaciente))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	

}
