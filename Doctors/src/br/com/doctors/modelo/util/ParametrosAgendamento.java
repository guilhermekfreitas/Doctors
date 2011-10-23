package br.com.doctors.modelo.util;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ParametrosAgendamento {
	private LocalTime horaInicioAtendimento;
	private LocalTime horaFimAtendimento;
	private DateTimeFormatter horaFormatter;
	private DateTimeFormatter dataFormatter;
	private Minutes minutosPorConsulta;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	
	public ParametrosAgendamento() {
	}
	
	public static ParametrosAgendamento getParametrosDefault(){
		
		ParametrosAgendamento padrao = new ParametrosAgendamento();
		padrao.setHoraInicioAtendimento(new LocalTime(8, 0));
		padrao.setHoraFimAtendimento(new LocalTime(17, 30));
		padrao.setDataInicial(new LocalDate().plusDays(1));
		padrao.setDataFinal(new LocalDate(padrao.getDataInicial()).plusMonths(2));
		padrao.setMinutosPorConsulta(Minutes.minutes(30));
		padrao.setDataFormatter(DateTimeFormat.forPattern("dd/MM/yyyy"));
		padrao.setHoraFormatter(DateTimeFormat.forPattern("HH:mm"));
		
		return padrao;
	}
	
	public LocalTime getHoraInicioAtendimento() {
		return horaInicioAtendimento;
	}
	public LocalTime getHoraFimAtendimento() {
		return horaFimAtendimento;
	}
	public DateTimeFormatter getHoraFormatter() {
		return horaFormatter;
	}
	public DateTimeFormatter getDataFormatter() {
		return dataFormatter;
	}
	public Minutes getMinutosPorConsulta() {
		return minutosPorConsulta;
	}
	public LocalDate getDataInicial() {
		return dataInicial;
	}
	public LocalDate getDataFinal() {
		return dataFinal;
	}
	public void setHoraInicioAtendimento(LocalTime horaInicioAtendimento) {
		this.horaInicioAtendimento = horaInicioAtendimento;
	}
	public void setHoraFimAtendimento(LocalTime horaFimAtendimento) {
		this.horaFimAtendimento = horaFimAtendimento;
	}
	public void setHoraFormatter(DateTimeFormatter horaFormatter) {
		this.horaFormatter = horaFormatter;
	}
	public void setDataFormatter(DateTimeFormatter dataFormatter) {
		this.dataFormatter = dataFormatter;
	}
	public void setMinutosPorConsulta(Minutes minutosPorConsulta) {
		this.minutosPorConsulta = minutosPorConsulta;
	}
	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}
	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public LocalTime proximaConsultaApos(LocalTime horario){
		return horario.plus(minutosPorConsulta);
	}
	
}
