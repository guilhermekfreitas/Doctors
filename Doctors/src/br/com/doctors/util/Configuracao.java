package br.com.doctors.util;

import java.util.Collection;
import java.util.Properties;
import java.util.Map.Entry;

public interface Configuracao {
	public Collection<Entry<Object,Object>> getAllPropriedades();
}
