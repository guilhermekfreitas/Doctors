package br.com.doctors.converters.notificacao;

import java.util.ArrayList;
import java.util.List;

import br.com.doctors.dao.util.NotificacaoDao;
import br.com.doctors.modelo.util.Notificacao;
import br.com.doctors.modelo.util.ParametrosAgendamento;

public class NotificacaoConverter {

	private ParametrosAgendamento parametros;
	private NotificacaoDao daoNotificacao;

	public NotificacaoConverter(ParametrosAgendamento parametros,
			NotificacaoDao daoNotificacao) {
		this.parametros = parametros;
		this.daoNotificacao = daoNotificacao;
	}

	public List<NotificacaoJSon> buscaNotificacoesPara(Long idMedico) {

		List<Notificacao> listaNotificacoes = getNotificacoes(idMedico);
		
		List<NotificacaoJSon> notificacaoesJSon = new ArrayList<NotificacaoJSon>();
		
		for( Notificacao notificacao : listaNotificacoes ){
			NotificacaoJSon json = new NotificacaoJSon(parametros, notificacao);
			notificacaoesJSon.add(json);
		}
		
		return notificacaoesJSon;
	}

	private List<Notificacao> getNotificacoes(Long idMedico) {
		return daoNotificacao.buscaPorMedico(idMedico);
	}

}
