package Tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Cliente;

public class CadastroCliente04 {
	public static void main(String... args) {
		  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
		  EntityManager entityManager = entityManagerFactory.createEntityManager();
		 
		  Cliente cliente = entityManager.find(Cliente.class, 2);
		  
		  entityManager.getTransaction().begin();
		  entityManager.remove(cliente);
		  entityManager.getTransaction().commit();
		 
		  entityManager.close();
		  entityManagerFactory.close();
		}
}
