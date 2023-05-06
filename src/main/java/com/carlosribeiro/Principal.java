package com.carlosribeiro;

import com.carlosribeiro.dao.AutorDAO;
import com.carlosribeiro.excecao.AutorNaoEncontradoException;
import com.carlosribeiro.modelo.Autor;
import com.carlosribeiro.util.FabricaDeDAOs;
import com.carlosribeiro.util.Util;
import corejava.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public class Principal
{	public static void main (String[] args) 
	{
		Logger logger = LoggerFactory.getLogger(Principal.class);
		logger.error("Mensagem de log emitida utilizando o LOG4J");
		// fatal - error - warning - info - debug

		String nome;
		String instituicao;
		String dataCadastro;
		Autor umAutor;

		AutorDAO autorDAO = FabricaDeDAOs.getDAO(AutorDAO.class);

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um autor");
			System.out.println("2. Alterar um autor");
			System.out.println("3. Remover um autor");
			System.out.println("4. Listar todos os autores");
			System.out.println("5. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um número entre 1 e 5:");
					
			switch (opcao)
			{	case 1:
				{
					nome = Console.readLine('\n' + 
						"Informe o nome do autor: ");
					instituicao = Console.readLine(
						"Informe a instituição do autor: ");
						
					umAutor = new Autor(nome, instituicao);
					
					autorDAO.inclui(umAutor);
					
					System.out.println('\n' + "Autor número " +
					    umAutor.getId() + " incluído com sucesso!");

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do autor que você deseja alterar: ");
										
					try
					{
						umAutor = autorDAO.recuperaUmAutor(resposta);
					}
					catch(AutorNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umAutor.getId() +
						"    Nome = " + umAutor.getNome() +
						"    Instituicao = " + umAutor.getInstituicao());
												
					System.out.println('\n' + "O que você deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Instituicao");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um número de 1 a 2:");

					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
								readLine("Digite o novo nome: ");

							umAutor.setNome(novoNome);

							try
							{
								autorDAO.altera(umAutor);

								System.out.println('\n' +
									"Alteração de nome efetuada com sucesso!");
							}
							catch(AutorNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}

							break;

						case 2:
							String novaInstituicao = Console.
									readLine("Digite a nova instituição: ");

							umAutor.setInstituicao(novaInstituicao);

							try
							{
								autorDAO.altera(umAutor);

								System.out.println('\n' +
									"Alteração de lance mínimo efetuada " +
									"com sucesso!");
							}
							catch(AutorNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}

							break;

						default:
							System.out.println('\n' + "Opção inválida!");
					}

					break;
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do autor que você deseja remover: ");
									
					try
					{
						umAutor = autorDAO.recuperaUmAutor(resposta);
					}
					catch(AutorNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umAutor.getId() +
						"    Nome = " + umAutor.getNome());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remoção do autor?");

					if(resp.equals("s"))
					{	try
						{
							autorDAO.exclui (umAutor.getId());
							System.out.println('\n' + 
								"Autor removido com sucesso!");
						}
						catch(AutorNaoEncontradoException e)
						{	System.out.println('\n' + e.getMessage());
						}
					}
					else
					{	System.out.println('\n' + "Autor não removido.");
					}
					
					break;
				}

				case 4:
				{
					List<Autor> autors = autorDAO.recuperaAutores();

//                  Utilizando um consumer. Consumer é uma interface funcional. Ela recebe um
//                  argumento e não retorna nada. Para que um valor seja aceito pelo Consumer
//                  deve ser executado o método accept.


//                  Utilizando method reference. Method references são expressões que possuem
//                  o mesmo tratamento de expressões lambda, mas em vez de prover um corpo  à
//                  expressão lambda, eles (os method references) referenciam um método existente
//                  pelo nome.

					for (Autor autor : autors)
					{
						System.out.println('\n' +
							"Id = " + autor.getId() +
							"  Nome = " + autor.getNome() +
							"  Instituição = " + autor.getInstituicao());
					}

					break;
				}
//
				case 5:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Opção inválida!");
			}
		}		
	}
}
