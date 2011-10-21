package br.com.doctors.util.json;

import java.util.ArrayList;
import java.util.List;

public class JQRow {
	private Long id;
	private List<String> cells;
	
	public JQRow() {
		cells = new ArrayList<String>();
	}
	
	public JQRow(Long id, List<String> cells) {
		this.id = id;
		this.cells = cells;
	}
	
	public Long getId() {
		return id;
	}
	public List<String> getCells() {
		return cells;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCells(List<String> cell) {
		this.cells = cell;
	}
	
}
