package br.com.doctors.modelo.administracao;

public class UsuarioInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1598865591282299067L;

	public UsuarioInvalidoException() {
	}
	
	public UsuarioInvalidoException(String message){
		super(message);
	}
}
