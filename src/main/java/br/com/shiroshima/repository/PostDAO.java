package br.com.shiroshima.repository;

import br.com.shiroshima.entity.Post;
import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.DAOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.Optional;

public class PostDAO extends DAO<Post, Long> {

    public PostDAO(EntityManager em) {
        super(em);
    }

    public Optional<Post> findById(Long id) {
        try {
            Post post = em.find(Post.class, id);
            return Optional.ofNullable(post);
        } catch (PersistenceException e) {
            throw new DAOException("Error finding post with id: " + id);
        }
    }

    public Optional<User> findByTitle(String title) {
        try {
            User possiblePost = (User) em.createQuery("SELECT post from Post post where post.title = ?1")
                    .setParameter(1, title)
                    .getSingleResult();
            return Optional.ofNullable(possiblePost);
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching posts with title: " + title);
        }
    }
}
