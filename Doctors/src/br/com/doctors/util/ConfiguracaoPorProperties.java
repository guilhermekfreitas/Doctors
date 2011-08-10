package br.com.doctors.util;

import java.util.Map.Entry;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

public class ConfiguracaoPorProperties implements Configuracao{
	private Properties properties;
	
	public ConfiguracaoPorProperties(Properties prop){
		this.properties = prop;
	}
	
	public Collection<Entry<Object,Object>> getAllPropriedades(){
		return properties.entrySet();
	}
	
	public boolean isEmpty(){
		return properties.isEmpty();
	}
	
	public String getPropriedade(String nomeParametro){
		return properties.getProperty(nomeParametro);
	}
	
	public void setPropriedade(String nomeParametro, String valor){
		if (properties.containsKey(nomeParametro)){
			properties.setProperty(nomeParametro, valor);
		} else {
			properties.put(nomeParametro, valor);
		}
	}
	
}

