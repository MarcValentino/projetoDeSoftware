package com.carlosribeiro.dao;

import com.carlosribeiro.excecao.AutorNaoEncontradoException;
import com.carlosribeiro.excecao.EstadoDeObjetoObsoletoException;
import com.carlosribeiro.modelo.Autor;

import java.util.List;


public interface AutorDAO
{
	long inclui(Autor umAutor);
	void altera(Autor umAutor) throws AutorNaoEncontradoException, EstadoDeObjetoObsoletoException;
	void exclui(long id) throws AutorNaoEncontradoException;
	public List<Autor> buscaAutor(String palavraBuscada, int escolha) throws AutorNaoEncontradoException;
	Autor recuperaUmAutor(long numero) throws AutorNaoEncontradoException;
	List<Autor> recuperaAutores();
}