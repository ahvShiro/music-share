package br.com.shiroshima.repository;

import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.DAOException;
import jakarta.persistence.*;

import java.util.Optional;

public class UserDAO extends DAO<User, Long> {

    public UserDAO() {
        super();
    }

    public Optional<User> findByUsername(String username) {
        try {
            User possibleUser = (User) em.createQuery("SELECT user from User user where user.username = ?1")
                    .setParameter(1, username)
                    .getSingleResult();
            return Optional.ofNullable(possibleUser);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching users with username: " + username);
        }
    }
}
