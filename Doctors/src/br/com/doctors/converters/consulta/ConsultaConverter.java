package br.com.doctors.converters.consulta;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class ConsultaConverter {

	private AgendamentoDao daoAgendamento;
	private ParametrosAgendamento parametros;

	public ConsultaConverter(AgendamentoDao daoAgendamento, ParametrosAgendamento parametros) {
		this.daoAgendamento = daoAgendamento;
		this.parametros = parametros;
	}
	
	public List<ConsultaJSon> buscarHistorico(Long idMedico, Long idPaciente, LocalDate dataInicial, LocalDate dataFinal){
		
		List<Agendamento> listaAgendamentos = getConsultas(idMedico, idPaciente, dataInicial, dataFinal);
		
		List<ConsultaJSon> consultasJSon = new ArrayList<ConsultaJSon>();
		
		for( Agendamento agendamento : listaAgendamentos ){
			ConsultaJSon json = new ConsultaJSon(agendamento, parametros);
			consultasJSon.add(json);
		}
		
		return consultasJSon;
	}

	public List<Agendamento> getConsultas(Long idMedico, Long idPaciente,
			LocalDate dataInicial, LocalDate dataFinal) {
		List<Agendamento> listaAgendamentos;
		if (ehPraTodosMedicos(idMedico)){
			listaAgendamentos = daoAgendamento.agendamentosPara(idPaciente, dataInicial, dataFinal);
		} else {
			listaAgendamentos = daoAgendamento.agendamentosPara(idMedico, idPaciente, dataInicial, dataFinal);
		}
		return listaAgendamentos;
	}

	public boolean ehPraTodosMedicos(Long idMedico) {
		return idMedico == 0;
	}

}
