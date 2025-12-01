package br.com.shiroshima.repository;

import br.com.shiroshima.entity.Post;
import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.DAOException;
import br.com.shiroshima.security.AuthContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Optional;

public class PostDAO extends DAO<Post, Long> {

    public PostDAO() {
        super();
    }

    public Optional<Post> findById(Long id) {
        try {
            Post post = em.find(Post.class, id);
            return Optional.ofNullable(post);
        } catch (PersistenceException e) {
            throw new DAOException("Error finding post with id: " + id);
        }
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

    public List<Post> findOwn() {
        User owner = AuthContext.getCurrentUser();
        try {
            return findByOwner(owner);
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching posts for user: " + owner.getUsername());
        }
    }

    public List<Post> findAll() {
        try {
            return em.createQuery("SELECT p FROM Post p ORDER BY p.createdAt DESC", Post.class)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching all posts");
        }
    }
}
