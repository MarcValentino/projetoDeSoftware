package com.carlosribeiro.dao;

import com.carlosribeiro.excecao.AutorNaoEncontradoException;
import com.carlosribeiro.modelo.Autor;

import java.util.List;


public interface AutorDAO
{
	long inclui(Autor umAutor);
	void altera(Autor umAutor) throws AutorNaoEncontradoException;
	void exclui(long id) throws AutorNaoEncontradoException;
	Autor recuperaUmAutor(long numero) throws AutorNaoEncontradoException;
	List<Autor> recuperaAutores();
}