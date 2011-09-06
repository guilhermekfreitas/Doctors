package br.com.doctors.services;

import java.util.List;
import java.util.Map;
import org.joda.time.LocalDate;
import br.com.doctors.modelo.agendamento.Agendamento;

public interface AgendaConverter {

	public Map<LocalDate, ? extends AgendamentoCommand> convertToMap(List<Agendamento> horariosConfirmados);

	public <T extends AgendamentoCommand> T preencheHorariosDoDia(LocalDate dataAtual, Map<LocalDate, T> horariosOcupados);

}
