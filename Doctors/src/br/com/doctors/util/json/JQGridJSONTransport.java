package br.com.doctors.util.json;

import java.util.ArrayList;
import java.util.List;


public class JQGridJSONTransport {
	private String page;  // paginas requisitadas
	private String total; // total de paginas
	private String records;
	private List<JQRow> rows;
	
	public JQGridJSONTransport(){
		rows = new ArrayList<JQRow>();
		page = "1";
	}
	
	public void addJSONObjects(List<JSONObject> lista){
		
		for (JSONObject objeto : lista){
			JQRow row = new JQRow(objeto.getId(), objeto.getCells());
			rows.add(row);
		}
		
		Integer tamLista = lista.size();
		records = tamLista.toString();
		total = Integer.toString((tamLista / 10)+1);
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
