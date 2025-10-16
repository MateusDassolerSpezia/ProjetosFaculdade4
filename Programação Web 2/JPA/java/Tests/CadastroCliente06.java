package Tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Cliente;

public class CadastroCliente06 {
	public static void main(String... args) {
		  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
		  EntityManager entityManager = entityManagerFactory.createEntityManager();
		 
		  Cliente cliente = new Cliente();
		  cliente.setId(1);
		  cliente.setNome("Autopeças Estrada");
		  
		  entityManager.getTransaction().begin();
		  entityManager.merge(cliente);
		  entityManager.getTransaction().commit();
		 
		  entityManager.close();
		  entityManagerFactory.close();
		}
}
