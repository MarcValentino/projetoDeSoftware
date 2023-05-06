package com.carlosribeiro.util;

import com.carlosribeiro.dao.impl.JPAAutorDAO;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class FabricaDeDAOs
{	
	public static <T> T getDAO(Class<T> tipo)
	{
		return (T) new JPAAutorDAO();
		// Permite que a gente investigue as classes no package "com.carlosribeiro.dao.impl"

		// Nesse momento a variável tipo estará valendo Autor.class
		// A linha abaixo verifica se no package "com.carlosribeiro.dao.impl"
		// existe uma classe subtipo de Autor.class.
		// Vai retornar um Set contendo a classe JPAAutorDAO.

		// Não pode haver mais de uma classe nesse package que estenda AutorDAO
		// caso contrário a gente não saberia qual utilizar.

		// Retorna a classe JPAAutorDAO na variável classe.

		// Instancia um objeto do tipo JPAAutorDAO usando o construtor Padrão
	}
}
