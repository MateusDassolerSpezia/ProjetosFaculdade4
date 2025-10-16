package Tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Cliente;

public class CadastroCliente05 {
	public static void main(String... args) {
		  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
		  EntityManager entityManager = entityManagerFactory.createEntityManager();
		 
		  Cliente cliente = entityManager.find(Cliente.class, 1);
		  
		  entityManager.getTransaction().begin();
		  cliente.setNome("Autopeças Rodovia");
		  entityManager.getTransaction().commit();
		 
		  entityManager.close();
		  entityManagerFactory.close();
		}
}
