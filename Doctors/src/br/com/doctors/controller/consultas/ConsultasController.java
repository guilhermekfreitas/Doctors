package br.com.doctors.controller.consultas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.doctors.controller.agendamento.AgendamentosController;
import br.com.doctors.converters.consulta.ConsultaConverter;
import br.com.doctors.converters.consulta.ConsultaJSon;
import br.com.doctors.dao.agendamento.AgendamentoDao;
import br.com.doctors.dao.consultas.AtestadoDao;
import br.com.doctors.dao.consultas.ConsultaDao;
import br.com.doctors.dao.consultas.ExameDao;
import br.com.doctors.dao.consultas.ReceitaDao;
import br.com.doctors.modelo.agendamento.Agendamento;
import br.com.doctors.modelo.consultas.Atestado;
import br.com.doctors.modelo.consultas.Consulta;
import br.com.doctors.modelo.consultas.Exame;
import br.com.doctors.modelo.consultas.Receita;
import br.com.doctors.modelo.util.ParametrosAgendamento;
import br.com.doctors.util.json.JQGridJSONConverter;

/**
 * 
 * @author Jonathan, Guilherme
 * 
 */
@Resource
public class ConsultasController {
	private ConsultaDao daoConsulta;
	private Result result;
	private Validator validator;
	private AgendamentoDao daoAgendamento;
	private ExameDao daoExame;
	private ParametrosAgendamento parametros;
	private AtestadoDao daoAtestado;
	private ReceitaDao daoReceita;

	public ConsultasController(ConsultaDao daoConsulta,
			AgendamentoDao daoAgendamento, Result result, Validator validator,
			ExameDao daoExame, AtestadoDao daoAtestado, ReceitaDao daoReceita) {
		this.daoConsulta = daoConsulta;
		this.daoAgendamento = daoAgendamento;
		this.result = result;
		this.validator = validator;
		this.daoExame = daoExame;
		this.daoAtestado = daoAtestado;
		this.daoReceita = daoReceita;
		this.parametros = ParametrosAgendamento.getParametrosDefault();
	}

	@Get
	@Path({ "/consultas" })
	public void list() {
		result.include("consultas", daoConsulta.listaTudo());
	}

	@Get
	@Path("/consultas/novo/{idAgendamento}")
	public void cadastro(Long idAgendamento) {
		result.include("agendamento", daoAgendamento.carrega(idAgendamento));
		result.include("consultas", daoConsulta.listaTudo());
	}

	@Post
	@Path("/consultas")
	public void adiciona(final Consulta consulta, Collection<String> exames) {

		System.out.println(consulta.getAgendamento());

		System.out.println("======================================");
		// System.out.println("Consulta:" + consulta);

		List<Exame> examesList = new ArrayList<Exame>();
		for (String descricao : exames) {
			System.out.println(descricao);
			Exame exame = new Exame(descricao);
			exame.setConsulta(consulta);
			daoExame.adiciona(exame);
			examesList.add(exame);
		}
		// consulta.setExames(examesList);

		// validator.checking(new Validations(){{
		// that(consulta.getNome() != null && consulta.getNome().length() >= 3,
		// "paciente.nome", "nome.obrigatorio");
		//
		// // mais validações
		// }});
		// validator.onErrorUsePageOf(this).cadastro();

		daoConsulta.adiciona(consulta);
		result.redirectTo(ConsultasController.class).list();
	}

	public ConsultaDao getDaoConsulta() {
		return daoConsulta;
	}

	public void setDaoConsulta(ConsultaDao daoConsulta) {
		this.daoConsulta = daoConsulta;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Get
	@Path("/consultas/{id}")
	public Consulta edit(Long id) {
		return daoConsulta.carrega(id);
	}

	@Put
	@Path("/consultas/{consulta.id}")
	public void alterar(final Consulta consulta) {
		// validator.checking(new Validations(){{
		// that(consulta.getNome() != null && consulta.getNome().length() >= 3,
		// "paciente.nome", "nome.obrigatorio");
		//
		// // mais validações
		// }});
		// validator.onErrorUsePageOf(this).edit(consulta.getId());

		daoConsulta.atualiza(consulta);
		result.redirectTo(ConsultasController.class).list();
	}

	@Path("/consultas/remover/{id}")
	public void remover(Long id) {
		Consulta consulta = daoConsulta.carrega(id);
		daoConsulta.remove(consulta);
		result.redirectTo(ConsultasController.class).list();
	}

	@Post
	@Path("/consultas/efetuarConsulta")
	public void efetuarConsultar(Consulta consulta) {

		Agendamento agendamento = daoAgendamento.carrega(consulta
				.getAgendamento().getId());
		agendamento.setConsulta(consulta);
		// atualizar status do agendamento tbm
		daoAgendamento.atualiza(agendamento);

		System.out.println(consulta);
		daoConsulta.adiciona(consulta);

		// temporário
		if (consulta.getExames() != null) {
			for (Exame exame : consulta.getExames()) {
				exame.setConsulta(consulta);
				daoExame.adiciona(exame);
			}
		}

		if (consulta.getAtestados() != null) {
			for (Atestado atestado : consulta.getAtestados()) {
				atestado.setConsulta(consulta);
				daoAtestado.adiciona(atestado);
			}
		}

		if (consulta.getReceitas() != null) {
			for (Receita receita : consulta.getReceitas()) {
				receita.setConsulta(consulta);
				daoReceita.adiciona(receita);
			}
		}

		result.redirectTo(AgendamentosController.class).list();
	}

	@Post
	@Path("/consulta/consultarHistorico")
	public void consultarHistorico(Long idPaciente, Long idMedico,
			LocalDate dataInicial, LocalDate dataFinal) {

		System.out.printf(
				"idPaciente: %d, idMedico: %d, inicial:%s, final:%s\n",
				idPaciente, idMedico, dataInicial, dataFinal);

		ConsultaConverter consultaConverter = new ConsultaConverter(
				daoAgendamento, parametros);

		List<ConsultaJSon> consultasJSon = consultaConverter.buscarHistorico(
				idMedico, idPaciente, dataInicial, dataFinal);

		System.out.println(consultasJSon);

		JQGridJSONConverter jqgrid = new JQGridJSONConverter();
		jqgrid.addJSONObjects(consultasJSon);

		result.use(Results.json()).withoutRoot().from(jqgrid).include("rows")
		.include("rows.cells").serialize();

	}

	public void consulta() {

	}

}
