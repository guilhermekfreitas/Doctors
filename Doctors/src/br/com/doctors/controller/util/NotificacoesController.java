package br.com.doctors.controller.util;

import java.util.List;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.view.Results;
import br.com.doctors.dao.administracao.FuncionarioDao;
import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.administracao.PerfilUsuarioDao;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.dao.util.NotificacaoDao;
import br.com.doctors.modelo.administracao.Funcionario;
import br.com.doctors.modelo.administracao.Medico;
import br.com.doctors.modelo.administracao.PerfilUsuario;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.util.Notificacao;
import br.com.doctors.util.UserSession;

@Resource
public class NotificacoesController {
	
	private Result result;
	private UserSession userSession;
	private NotificacaoDao daoNotificacao;
	private MedicoDao daoMedico;
	private AgendamentoDao daoAgendamento;
	private FuncionarioDao daoFuncionario;

	public NotificacoesController(Result result, MedicoDao daoMedico, 
							NotificacaoDao daoNotificacao, UserSession userSession,
							AgendamentoDao daoAgendamento, FuncionarioDao daoFuncionario) {
		this.result = result;
		this.daoNotificacao = daoNotificacao;
		this.userSession = userSession;
		this.daoMedico = daoMedico;
		this.daoFuncionario = daoFuncionario;
		this.daoAgendamento = daoAgendamento;
	}
	
	@Post
	@Path("/notificacoes/notifica")
	public void notificarMedico(Long idAgendamento){

		Notificacao notificacao = new Notificacao();

		Agendamento agendamento = daoAgendamento.carrega(idAgendamento);
		notificacao.setAgendamento(agendamento);
		
		Funcionario funcionario = getFuncionarioLogado();
		notificacao.setFuncionario(funcionario);
		
		daoNotificacao.adiciona(notificacao);
		
		result.use(Results.status()).accepted();
	}

	public Funcionario getFuncionarioLogado() {
		return daoFuncionario.carregaPorPerfil(userSession.getUsuario());
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
