package br.com.shiroshima;

import br.com.shiroshima.entity.User;
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

            try {
                User user = service.create("Xx_Junin_Ruindade_Pura_xX", "Senha@123", "Test user bio");
                System.out.println("User created: " + user);
            } catch (Exception e) {
                System.out.println("User might already exist: " + e.getMessage());
            }

            boolean authApproval = service.auth("Xx_Junin_Ruindade_Pura_xX", "Senha@123");
            System.out.println("Authentication successful: " + authApproval);
        }
    }
}