package br.com.doctors.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T> {
	void adiciona(T t);
	void remove(T t);
	void atualiza(T t);
	List<T> listaTudo();
	T procura(Serializable T);
}
