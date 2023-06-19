package com.carlosribeiro.dao.impl;

import com.carlosribeiro.dao.AutorDAO;
import com.carlosribeiro.excecao.AutorNaoEncontradoException;
import com.carlosribeiro.excecao.EstadoDeObjetoObsoletoException;
import com.carlosribeiro.modelo.Autor;
import com.carlosribeiro.util.FabricaDeEntityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;

public class JPAAutorDAO implements AutorDAO
{
	public long inclui(Autor umAutor) {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			// transiente - Objeto cujo valor do atributo identificador é null
			// persistente - Objeto que está sendo monitorado pelo entity manager
			// destacado - Objeto cujo valor do atributo identificador é diferente de null
			//             e não está sendo monitorado pelo entity manager.
			em = FabricaDeEntityManager.criarEntityManager();
			tx = em.getTransaction();
			tx.begin();
			// 1. Executar o comando insert
			// 2. Atribui ao id do autor o seu valor
			// 3. Acrescenta o objeto umAutor à lista de objetos monitorados do entity manager
			em.persist(umAutor);
			// System.out.println(umAutor.getId());
			// umAutor.setNome("abc");
			tx.commit();
			return umAutor.getId();
		} catch(RuntimeException e) {
			if (tx != null) {
				try {
					tx.rollback();
				}
				catch(RuntimeException ex)
				{ }
			}
			throw e;
		} finally {
			em.close();
		}
	}

	public Autor recuperaUmAutor(long numero) throws AutorNaoEncontradoException {
		EntityManager em = null;

		try
		{
			em = FabricaDeEntityManager.criarEntityManager();
			Autor umAutor = em.find(Autor.class, numero);

			// Características no método find():
			// 1. É genérico: não requer um cast.
			// 2. Retorna null caso a linha não seja encontrada no banco.

			if(umAutor == null)
			{	throw new AutorNaoEncontradoException("Autor não encontrado");
			}
			return umAutor;
		}
		finally
		{   em.close();
		}
	}

	public void altera(Autor umAutor) throws AutorNaoEncontradoException, EstadoDeObjetoObsoletoException {
		EntityManager em = null;
		EntityTransaction tx = null;
		Autor autor = null;
		try
		{
			em = FabricaDeEntityManager.criarEntityManager();
			tx = em.getTransaction();
			tx.begin();

			autor = em.find(Autor.class, umAutor.getId(), LockModeType.PESSIMISTIC_WRITE);

			if (autor == null) {
				tx.rollback();
				throw new AutorNaoEncontradoException("Autor não encontrado.");
			}
			// O merge entre nada e tudo é tudo. Ao tentar alterar um autor deletado ele será re-inserido
			// no banco de dados.
			em.merge(umAutor);

			tx.commit();
		}
		catch(OptimisticLockException e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			throw new EstadoDeObjetoObsoletoException("Conflito de versão, objeto já alterado por outro usuário!");
		}
		catch(RuntimeException e)
		{
			if (tx != null)
		    {   tx.rollback();
		    }
		    throw e;
		}
		finally
		{   em.close();
		}
	}
	public List<Autor> buscaAutor(String palavraBuscada, int escolha) throws AutorNaoEncontradoException{
		EntityManager em = null;

		try
		{
			em = FabricaDeEntityManager.criarEntityManager();

			List<Autor> listaResultados = null;

			if(escolha == 1) listaResultados = (List<Autor>) em.createQuery("Select a from Autor a where a.nome like :nome")
					.setParameter("nome", palavraBuscada)
					.getResultList();
			else listaResultados = (List<Autor>) em.createQuery("Select a from Autor a where a.instituicao like :instituicao")
					.setParameter("instituicao", palavraBuscada)
					.getResultList();

			if(listaResultados.isEmpty())
			{	throw new AutorNaoEncontradoException("Nenhum autor encontrado");
			}
			return listaResultados;
		}
		finally
		{   em.close();
		}
	}

	public void exclui(long numero) throws AutorNaoEncontradoException {
		EntityManager em = null;
		EntityTransaction tx = null;

		try
		{
			em = FabricaDeEntityManager.criarEntityManager();
			tx = em.getTransaction();
			tx.begin();

			Autor autor = em.find(Autor.class, numero);

			if(autor == null)
			{	tx.rollback();
				throw new AutorNaoEncontradoException("Autor não encontrado");
			}

			em.remove(autor);
			tx.commit();
		}
		catch(RuntimeException e)
		{
			if (tx != null)
		    {   tx.rollback();
		    }
		    throw e;
		}
		finally
		{   em.close();
		}
	}

	public List<Autor> recuperaAutores() {
		EntityManager em = null;

		try
		{	em = FabricaDeEntityManager.criarEntityManager();

			@SuppressWarnings("unchecked")
			List<Autor> autors = em
					.createQuery("select p from Autor p order by p.id")
					.getResultList();

			// Retorna um List vazio caso a tabela correspondente esteja vazia.

			return autors;
		}
		finally
		{   em.close();
		}
	}
}