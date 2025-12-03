package br.com.shiroshima.repository;

import br.com.shiroshima.entity.Post;
import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.DAOException;
import br.com.shiroshima.security.AuthContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class PostDAO extends DAO<Post, Long> {

    public PostDAO() {
        super();
    }

    public List<Post> findByTitle(String title) {
        try {
            String jpql = "SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(:title)";
            return em.createQuery(jpql, Post.class)
                    .setParameter("title", "%" + title + "%")
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching posts");
        }
    }

    public List<Post> findByOwner(User owner) {
        try {
            return em.createQuery("SELECT p FROM Post p WHERE p.owner = :owner ORDER BY p.createdAt DESC", Post.class)
                    .setParameter("owner", owner)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching posts for user: " + owner.getUsername());
        }
    }

    public List<Post> findByOwnerId(Long userId) {
        try {
            String jpql = "SELECT p FROM Post p JOIN FETCH p.owner WHERE p.owner.id = :userId ORDER BY p.createdAt DESC";
            return em.createQuery(jpql, Post.class).setParameter("userId", userId).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching posts from user with ID: " + userId);
        }
    }

    public List<Post> findOwn() {
        Long ownerId = AuthContext.getCurrentUser().getId();
        try {
            return findByOwnerId(ownerId);
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching your own posts");
        }
    }

    public List<Post> findByOwnerUsername(String username) {
        try {
            String jpql = "SELECT p FROM Post p JOIN FETCH p.owner WHERE LOWER(p.owner.username) = LOWER(:username) ORDER BY p.createdAt DESC";

            return em.createQuery(jpql, Post.class).setParameter("username", username).getResultList();

        } catch (PersistenceException e) {
            throw new DAOException("Error finding posts for username: " + username);
        }

    }
}
