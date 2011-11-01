package br.com.doctors.converters.consulta;

import java.util.Iterator;
import java.util.List;

import br.com.doctors.modelo.consultas.Atestado;
import br.com.doctors.modelo.consultas.Documento;

public class DocumentoConverter {

	private static String QUOTE = "\"";
	
	// deve retornar: [{tipo:'..', descricao:'...'}, {tipo:'..', descricao:'..'}]
	public String converte(List<Documento> documentos) {
		
		StringBuffer result = new StringBuffer("[");
		Iterator<Documento> it = documentos.iterator();
		for (;it.hasNext();){
			Documento doc = it.next();
			StringBuffer bf = new StringBuffer("{");
			bf.append(formataAtributo("tipo",doc.getTipo()));
			bf.append(",");
			bf.append(formataAtributo("descricao",doc.getDescricao()));
			bf.append("}");
			if (it.hasNext()){
				bf.append(",");
			}
			result.append(bf);
		}
		result.append("]");
		System.out.println(result);
		return result.toString();
	}

	private String formataAtributo(String nome, String valor){
		return String.format("\"%s\": \"%s\"",nome,valor);
	}
	
}
