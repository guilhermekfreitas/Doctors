package br.com.doctors.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.caelum.vraptor.ioc.Component;
import br.com.doctors.commands.AgendamentoCommand;
import br.com.doctors.converters.agendamento.AgendaCommandConverter;
import br.com.doctors.converters.agendamento.AgendaConverter;
import br.com.doctors.converters.agendamento.PreAgendamentoCommandConverter;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.modelo.agendamento.Agendamento;

@Component
public abstract class AgendamentoService {
	protected LocalTime horaInicioAtendimento;
	protected LocalTime horaFimAtendimento;
	protected DateTimeFormatter fmtHora;
	protected DateTimeFormatter fmtData;
	protected Minutes minutosPorConsulta;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private AgendamentoDao daoAgendamento;

	public AgendamentoService(AgendamentoDao daoAgendamento) {
		// pode vir parametrizado
		setPropriedadesDefault();
		this.daoAgendamento = daoAgendamento;
	}

	private void setPropriedadesDefault() {
		horaInicioAtendimento = new LocalTime(8, 0);
		horaFimAtendimento = new LocalTime(17, 30);
		dataInicial = new LocalDate().plusDays(1);
		dataFinal = new LocalDate(dataInicial).plusMonths(2);
		minutosPorConsulta = Minutes.minutes(30);
		fmtHora = DateTimeFormat.forPattern("HH:mm");
		fmtData = DateTimeFormat.forPattern("dd/MM/yyyy");
	}

	public void setDataInicial(LocalDate dataInicial){
		this.dataInicial = dataInicial;
	}

	public AgendamentoService comDataInicial(LocalDate dataInicial){
		this.dataInicial = dataInicial;
		return this;
	}

	
	public List<? extends AgendamentoCommand> getAgenda(Long idMedico){
		
		List<Agendamento> horariosConfirmados = daoAgendamento.agendamentosPara(idMedico);

		AgendaConverter converter = getAgendaConverter();
		Map<LocalDate, ? extends AgendamentoCommand> horariosAgrupadosPorDia = converter.converteHorariosParaMap(horariosConfirmados);

		List<AgendamentoCommand> agenda = new ArrayList<AgendamentoCommand>();

		LocalDate dataAtual = new LocalDate(dataInicial);
		while (!dataAtual.isAfter(dataFinal)){
			AgendamentoCommand registroDoDiaAtual = converter.preencheHorariosDoDia(dataAtual,horariosAgrupadosPorDia);
			agenda.add(registroDoDiaAtual);
			dataAtual = dataAtual.plusDays(1); // vai p/ próximo dia
		}
		
		return agenda;
	}

	protected abstract AgendaConverter getAgendaConverter();

}
