package br.com.shiroshima.repository;

import br.com.shiroshima.entity.User;
import jakarta.persistence.EntityManager;

public class UserDAO extends DAO<User, Long> {

    public UserDAO(EntityManager em) {
        super(em);
    }
}
