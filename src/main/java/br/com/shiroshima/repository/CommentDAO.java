package br.com.shiroshima.repository;

import br.com.shiroshima.entity.Comment;
import br.com.shiroshima.exception.DAOException;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Optional;

public class CommentDAO extends DAO<Comment, Long> {

    public CommentDAO() {
        super();
    }

    public List<Comment> findAll() {
        try {
            return em.createQuery("SELECT c FROM Comment c ORDER BY c.createdAt DESC", Comment.class).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching all comments");
        }
    }

    public Optional<Comment> findById(Long id) {
        try {
            Comment comment = em.find(Comment.class, id);
            return Optional.ofNullable(comment);
        } catch (PersistenceException e) {
            throw new DAOException("Error finding comment with id: " + id);
        }
    }

    // Tá bom só esse search né?
    public List<Comment> findByPostId(Long postId) {
        try {
            String jpql = "SELECT c FROM Comment c JOIN FETCH c.post WHERE c.post.id = :postId ORDER BY c.createdAt DESC";
            return em.createQuery(jpql, Comment.class).setParameter("postId", postId).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching comments from post with ID: " + postId);
        }
    }

    public List<Comment> findByContent(String query) {
        try {
            String jpql = "SELECT c FROM Comment c WHERE LOWER(c.content) LIKE LOWER(:query)";
            return em.createQuery(jpql, Comment.class)
                    .setParameter("query", "%" + query + "%")
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching comments");
        }
    }

     public List<Comment> findByUsername(String username) {
        try {
            String jpql = "SELECT c FROM Comment c JOIN FETCH c.user WHERE LOWER(c.user.username) = LOWER(:username) ORDER BY c.createdAt DESC";

            return em.createQuery(jpql, Comment.class).setParameter("username", username).getResultList();

        } catch (PersistenceException e) {
            throw new DAOException("Error finding comments for username: " + username);
        }
    }
}
