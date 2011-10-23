package br.com.doctors.util.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class JQRow {
	private Long id;
	private Map<String,String> cells;
	
	public JQRow(Long id, Map<String,String> cells) {
		this.id = id;
		this.cells = cells;
	}
	

	public Long getId() {
		return id;
	}
	public Map<String,String> getCells() {
		return cells;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCells(Map<String,String> cell) {
		this.cells = cell;
	}
	
	@Override
	public String toString() {
		return String.format("id:%d cells:[%s]", id, cells);
	}
}
