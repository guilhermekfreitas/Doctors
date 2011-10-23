package br.com.doctors.util.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class JQGridJSONConverter {
	private String page;  // paginas requisitadas
	private String total; // total de paginas
	private String records;
	private List<JQRow> rows;
	
	public JQGridJSONConverter(){
		rows = new ArrayList<JQRow>();
		page = "1";
		total = "1";
	}
	
	public void addJSONObjects(List<? extends JSONObject> lista){
		
		for (JSONObject objeto : lista){
			JQRow row = new JQRow(objeto.getId(), objeto.getCells());
			System.out.println(row);
			rows.add(row);
		}
		
		records = new Integer(lista.size()).toString();
	}
	
	public String getPage() {
		return page;
	}

	public String getRecords() {
		return records;
	}

	public List<JQRow> getRows() {
		return rows;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setRecords(String records) {
		this.records = records;
	}
	
	@Override
	public String toString() {
		return String.format("page=%s total=%s records=%s rows=%s\n", page, total, records, rows);
	}
}
