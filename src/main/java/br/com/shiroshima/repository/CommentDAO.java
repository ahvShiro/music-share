package br.com.shiroshima.repository;

import br.com.shiroshima.entity.Comment;
import br.com.shiroshima.entity.Post;
import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.DAOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.Optional;

public class CommentDAO extends DAO<Comment, Long> {

    public CommentDAO(EntityManager em) {
        super(em);
    }

    public Optional<Comment> findById(Long id) {
        try {
            Comment comment = em.find(Comment.class, id);
            return Optional.ofNullable(comment);
        } catch (PersistenceException e) {
            throw new DAOException("Error finding comment with id: " + id);
        }
    }

}
