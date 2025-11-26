package br.com.shiroshima.repository;

import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.DAOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import java.util.Optional;

public class UserDAO extends DAO<User, Long> {

    public UserDAO(EntityManager em) {
        super(em);
    }

    public Optional<User> findById(Long id) {
        try {
            User user = em.find(User.class, id);
            return Optional.ofNullable(user);
        } catch (PersistenceException e) {
            throw new DAOException("Error finding user with id: " + id);
        }
    }

    public Optional<User> findByUsername(String username) {
        try {
            User possibleUser = (User) em.createQuery("SELECT user from User user where user.username = ?1")
                    .setParameter(1, username)
                    .getSingleResult();
            return Optional.ofNullable(possibleUser);
        } catch (NoResultException e) {
            // Nenhum usuário encontrado, retorna Optional vazio
            return Optional.empty();
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching users with username: " + username);
        }
    }
}
