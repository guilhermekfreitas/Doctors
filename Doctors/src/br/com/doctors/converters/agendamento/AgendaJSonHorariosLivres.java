package br.com.doctors.converters.agendamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.doctors.modelo.util.ParametrosAgendamento;

public class AgendaJSonHorariosLivres {

	private ParametrosAgendamento parametros;
	private Map<LocalTime,HorarioJsonSimples> mapaHorariosLivres;
	private Long contador;
	
	public AgendaJSonHorariosLivres(ParametrosAgendamento parametros) {
		this.parametros = parametros;
		this.mapaHorariosLivres = new TreeMap<LocalTime,HorarioJsonSimples>();
		contador = new Long(1L);
	}

	public List<HorarioJsonSimples> getHorariosJson(){ 
		return new ArrayList<HorarioJsonSimples>(mapaHorariosLivres.values());
	}
	
	public void adicionaHorarioLivre(LocalDate data, LocalTime horaAtual){
		HorarioJsonSimples horarioJson = new HorarioJsonSimples(contador++, data, horaAtual, parametros);
		mapaHorariosLivres.put(horaAtual, horarioJson);
	}

}
