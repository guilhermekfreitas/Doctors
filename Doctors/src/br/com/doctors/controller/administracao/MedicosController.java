package br.com.doctors.controller.administracao;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.doctors.dao.administracao.ConvenioDao;
import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.dao.administracao.MedicoDao;
import br.com.doctors.modelo.administracao.Medico;

@Resource
public class MedicosController {
	private Result result;
	private Validator validator;
	private MedicoDao dao;

	public MedicosController(MedicoDao dao, Result result, Validator validator) {
		this.dao = dao;
		this.result = result;
		this.validator = validator;
	}
	
	@Get @Path({"/medicos"})
	public void list() {
		result.include("medicos", dao.listaTudo());
	}
	
	@Get @Path("/medicos/novo")
	public void cadastro() {
	}
	
	@Post @Path("/medicos")
	public void adiciona(final Medico medico){
		
		System.out.println("-======================================");
		System.out.println("Medico:" + medico );
		
		dao.adiciona(medico);
		result.redirectTo(MedicosController.class).list();
	}
	
	@Get @Path("/medicos/{id}")
	public Medico edit(Long id){
		return dao.carrega(id);
	}
	
	@Put @Path("/medicos/{medico.id}")
	public void alterar(final Medico medico){
		
		dao.atualiza(medico);
		result.redirectTo(MedicosController.class).list();
	}
	
	@Path("/medicos/remover/{id}")
	public void remover(Long id){
		Medico medico = dao.carrega(id);
		dao.remove(medico);
		result.redirectTo(MedicosController.class).list();
	}
}
