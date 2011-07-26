package br.com.doctors.dao.util;

import java.io.Serializable;
import java.util.List;

/***
 * 
 * @author Guilherme
 *
 */
public interface Dao<T> {
	void adiciona(T t);
	void remove(T t);
	void atualiza(T t);
	List<T> listaTudo();
	T carrega(Serializable T);
}
