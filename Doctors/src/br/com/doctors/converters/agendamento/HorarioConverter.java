package br.com.doctors.converters.agendamento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class HorarioConverter {

	private ParametrosAgendamento parametros;
	private AgendamentoDao daoAgendamento;
	private LocalTime horarioInicial;
	private LocalTime horarioFinal;

	public HorarioConverter(ParametrosAgendamento parametros, AgendamentoDao daoAgendamento) {
		this.parametros = parametros;
		this.daoAgendamento = daoAgendamento;
		this.horarioInicial = parametros.getHoraInicioAtendimento();
		this.horarioFinal = parametros.getHoraFimAtendimento();
	}
	
	
	public List<HorarioJsonImpl> getAgenda(Long idMedico, LocalDate data){
		
		LocalTime horaAtual = getHoraInicioExpediente();
		
		List<Agendamento> listAgendamentos = daoAgendamento.agendamentosPara(idMedico, data);
		
		AgendaDoDia agenda = new AgendaDoDia(listAgendamentos);
		
		Map<LocalTime,HorarioJsonImpl> mapaConvertidos = new TreeMap<LocalTime,HorarioJsonImpl>();
		while(estiverNoExpediente(horaAtual)){
			
			HorarioJsonImpl horarioJson = null;
			if (agenda.temAgendamentoEm(horaAtual)){
				Agendamento agendamento = agenda.getAgendamento(horaAtual);
				horarioJson = criaHorarioJson(agendamento);
			} else {
				horarioJson = criaHorarioJsonImplLivre(data,horaAtual);
			}
			mapaConvertidos.put(horaAtual, horarioJson );
			
			horaAtual = proximoHorario(horaAtual);
		}
		return new ArrayList<HorarioJsonImpl>(mapaConvertidos.values());
	}
	
	public List<HorarioJsonSimples> getHorariosLivres(Long idMedico, LocalDate data){
		
		LocalTime horaAtual = getHoraInicioExpediente();
		
		List<Agendamento> listAgendamentos = daoAgendamento.agendamentosPara(idMedico, data);
		
		AgendaDoDia agenda = new AgendaDoDia(listAgendamentos);
		Long contador = 1L;
		
		Map<LocalTime,HorarioJsonSimples> mapaHorariosLivres = new TreeMap<LocalTime,HorarioJsonSimples>();
		while(estiverNoExpediente(horaAtual)){
			
			HorarioJsonSimples horarioJson = null;
			if (!agenda.temAgendamentoEm(horaAtual)){
				horarioJson = criaHorarioJsonSimplesLivre(contador++,data,horaAtual);
				System.out.println(horarioJson);
				mapaHorariosLivres.put(horaAtual, horarioJson );
			}
			
			horaAtual = proximoHorario(horaAtual);
		}
		
		return new ArrayList<HorarioJsonSimples>(mapaHorariosLivres.values());
	}
	
	private HorarioJsonSimples criaHorarioJsonSimplesLivre(Long id, LocalDate data,
			LocalTime horaAtual) {
		return new HorarioJsonSimples(id, data, horaAtual, parametros);
	}


	private LocalTime proximoHorario(LocalTime horaAtual) {
		return parametros.proximaConsultaApos(horaAtual);
	}


	private HorarioJsonImpl criaHorarioJsonImplLivre(LocalDate data, LocalTime horaAtual) {
		return HorarioJsonImpl.criaHorarioLivre(data,horaAtual, parametros);
	}


	private HorarioJsonImpl criaHorarioJson(Agendamento agendamento) {
		return new HorarioJsonImpl(agendamento, parametros);
	}


	private boolean estiverNoExpediente(LocalTime horaAtual) {
		return  !horaAtual.isAfter(horarioFinal);
	}


	private LocalTime getHoraInicioExpediente() {
		return new LocalTime(horarioInicial);
	}
	
}
