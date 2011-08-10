package br.com.doctors.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfiguracaoFactory {
	public static Configuracao getConfiguracao(){
		
		Properties properties = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream("configuracoes.properties");
			properties.load(in); 
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return new ConfiguracaoPorProperties(properties);
	}
}
