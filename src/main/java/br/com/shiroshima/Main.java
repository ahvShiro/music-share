package br.com.shiroshima;

import br.com.shiroshima.controller.UserController;
import br.com.shiroshima.repository.UserDAO;
import br.com.shiroshima.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
             EntityManager em = emf.createEntityManager()) {

            UserDAO dao = new UserDAO(em);
            UserService service = new UserService(dao, em);

            // Inicia o controller com interface de login
            UserController controller = new UserController(service);
            controller.start();
        }
    }
}