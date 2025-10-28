package tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Loja;
import model.Produto;

public class Teste01 {

	public static void main(String... args) {
		  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
		  EntityManager entityManager = entityManagerFactory.createEntityManager();
		 
		  Produto produto = new Produto();
		  produto.setId(1);
		  produto.setNome("Produto 1");
		  produto.setDescricao("Produto descritivo 1");
		  produto.setValorCusto(5.0f);
		  produto.setValorVenda(10.0f);
		  produto.setEstoque(3);
		  
		  Loja loja = new Loja();
		  loja.setId(1);
		  loja.setNome("Loja 1");
		  loja.setDescricao("Descrição da loja 1");
		  loja.create(produto);
		  
		  entityManager.getTransaction().begin();
		  entityManager.persist(produto);
		  entityManager.getTransaction().commit();
		 
		  entityManager.close();
		  entityManagerFactory.close();
		}
}
