package br.com.doctors.converters.agendamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.util.ParametrosAgendamento;

public class AgendaJsonHorariosLivres implements AgendaJson{

	private ParametrosAgendamento parametros;
	private Map<LocalTime,HorarioJsonSimples> mapaHorariosLivres;
	private Long contador;
	private LocalDate data;
	
	public AgendaJsonHorariosLivres(ParametrosAgendamento parametros, LocalDate data) {
		this.parametros = parametros;
		this.data = data;
		this.mapaHorariosLivres = new TreeMap<LocalTime,HorarioJsonSimples>();
		contador = new Long(1L);
	}

	@Override
	public List<? extends HorarioJson> getHorariosJSON(){ 
		return new ArrayList<HorarioJsonSimples>(mapaHorariosLivres.values());
	}
	
	@Override
	public void addHorario(AgendaDoDia agenda, LocalTime horario) {
		if (!agenda.temAgendamentoEm(horario)){
			adicionaHorarioLivre(horario);
		}
	}

	private void adicionaHorarioLivre(LocalTime horaAtual){
		HorarioJsonSimples horarioJson = new HorarioJsonSimples(contador++, data, horaAtual, parametros);
		mapaHorariosLivres.put(horaAtual, horarioJson);
	}

}
