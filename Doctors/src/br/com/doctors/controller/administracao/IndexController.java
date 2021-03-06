package br.com.doctors.controller.administracao;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.swing.text.DateFormatter;

import org.joda.time.LocalDate;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.doctors.util.json.JQGridJSONConverter;
import br.com.doctors.util.json.JSONObject;

@Resource
public class IndexController {
	
	private Result result;

	public IndexController(Result result) {
		this.result = result;
	}
	
	@Path("/")
	public void index(){
		
	}
	
	@Path("/contato")
	public void contato(){
		
	}
	
	@Path("/conveniosassoc")
	public void conveniosAssociados(){
		
	}
		
	@Path("/lista")
	public void lista(){
		
	}
	
	@Path({"/getLista/{dia}/{mes}/{ano}", "/getLista"})
	public void getLista(Integer dia, Integer mes, Integer ano){
		
		System.out.println(dia+""+mes+""+ano);

		LocalDate date;
		if (dia != null){
			date = new LocalDate(ano, mes, dia);
		} else {
			date = new LocalDate(); // data atual
		}
		
		System.out.println(date);
		
		final class Cliente implements JSONObject {
			Long id;
			LocalDate invdate;
			Double amount;
			String note;
			public Long getId() {
				return id;
			}
			public LocalDate getInvdate() {
				return invdate;
			}
			public Double getAmount() {
				return amount;
			}
			public String getNote() {
				return note;
			}
			public void setId(Long id) {
				this.id = id;
			}
			public void setInvdate(LocalDate invdate) {
				this.invdate = invdate;
			}
			public void setAmount(Double amount) {
				this.amount = amount;
			}
			public void setNote(String note) {
				this.note = note;
			}
			@Override
			public Map<String, String> getCells() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		Cliente c = new Cliente();
		c.setId(1L);
		c.setAmount(60.4);
		c.setInvdate(new LocalDate());
		c.setNote("Anota��o");
		
		Cliente c2 = new Cliente();
		c2.setId(2L);
		c2.setAmount(30.4);
		c2.setInvdate(new LocalDate());
		c2.setNote("Anota��o 2");
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		list.add(c);
		list.add(c2);
		list.add(c2);
		
		JQGridJSONConverter jqgrid = new JQGridJSONConverter();
		
		jqgrid.addJSONObjects(list);
		
		result.use(Results.json()).withoutRoot().from(jqgrid).
			include("rows").include("rows.cells").include("total").serialize();
	}
	
	
	@Path("/test")
	public void test(){
		
	}
	
	@Path("/testJSONPost")
	public void testJsonPost(Long idMedico, LocalDate horario){
		
		System.out.println("DEBUG");
		System.out.println(idMedico);
		System.out.println(horario);
		
	}
	
	
	
	
}
