package br.com.doctors.controller.util;

import java.util.List;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.view.Results;
import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.util.NotificacaoDao;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.util.Notificacao;
import br.com.doctors.util.UserSession;

@Resource
public class NotificacoesController {
	
	private Result result;
	private NotificacaoDao daoNotificacao;
	private UserSession userSession;
	private MedicoDao daoMedico;

	public NotificacoesController(Result result, MedicoDao daoMedico, 
							NotificacaoDao daoNotificacao, UserSession userSession ) {
		this.result = result;
		this.daoNotificacao = daoNotificacao;
		this.userSession = userSession;
		this.daoMedico = daoMedico;
	}
	
	@Post
	@Path("/notificacoes/notifica")
	public void notificarMedico(Notificacao notificacao){
		// TO-DO
		daoNotificacao.adiciona(notificacao);
		
	}
	
	@Get
	@Path("/notificacoes/verifica")
	public void buscaNotificacoes(){
		
		Medico medicoLogado = daoMedico.buscaPorPerfil(userSession.getUsuario().getId());
		List<Notificacao> notificacoes = daoNotificacao.buscaPorMedico(medicoLogado.getId());
		
		result.use(Results.json()).from(notificacoes, "notificacoes").
						include("funcionario", "agendamento").serialize();
		
	}
}
