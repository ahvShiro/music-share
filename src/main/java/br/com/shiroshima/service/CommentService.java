package br.com.shiroshima.service;

import br.com.shiroshima.entity.Comment;
import br.com.shiroshima.entity.Post;
import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.BusinessRuleException;
import br.com.shiroshima.exception.ServiceException;
import br.com.shiroshima.repository.CommentDAO;
import br.com.shiroshima.security.AuthContext;

import java.util.List;
import java.util.Optional;

public class CommentService {

    private final CommentDAO dao;
    private final PostService postService;

    public CommentService(CommentDAO dao, PostService postService) {
        this.dao = dao;
        this.postService = postService;
    }

    public void validateContent(String content) {
        if (content == null) {
            throw new BusinessRuleException("Comment cannot be null");
        }
        if (content.length() > 250) {
            throw new BusinessRuleException("Comment must have less than 250 characters");
        }
    }

    public Comment create(String content, Long postId) {
        try {
            validateContent(content);
            User user = AuthContext.getCurrentUser();

            if (user == null) {
                throw new BusinessRuleException("You cannot do this action");
            }

            Post post = postService.findById(postId);

            if (post == null) {
                throw new BusinessRuleException("Post not found, cannot create comment!");
            }

            Comment comment = new Comment(content, post, user);
            return dao.save(comment);
        } catch (RuntimeException e) {
            throw new ServiceException("Failed to create comment: " + e.getMessage());
        }
    }

    // POR REGRA DE NEGÓCIO UM COMENTÁRIO NÃO PODE SER EDITADO, APENAS DELETADO
    // Alias marquei bobeira por não fazer um esquema de soft delete mas é a vida
    public void delete(Long id) {
        if (id == null || id <= (long) 0) {
            throw new BusinessRuleException("Invalid Id");
        }
        Comment comment = findById(id);
        if (comment == null) {
            throw new BusinessRuleException("Comment not found!");
        }
        
        User currentUser = AuthContext.getCurrentUser();
        if (currentUser == null) {
            throw new BusinessRuleException("You must be logged in to delete comments!");
        }
        
        if (!currentUser.getId().equals(comment.getUser().getId())) {
            throw new BusinessRuleException("You cannot delete other user's comments!");
        }

        dao.delete(comment);
    }

    public Comment findById(Long id) {
        if (id == null || id <= (long) 0) {
            throw new BusinessRuleException("Invalid Id");
        }
        Optional<Comment> post = dao.findById(id);
        return post.orElse(null);
    }

    public List<Comment> findAll() {
        return dao.findAll();
    }

    public List<Comment> findByPostId(Long postId) {
        if (postId == null || postId <= (long) 0) {
            throw new BusinessRuleException("Invalid Post Id");
        }
        return dao.findByPostId(postId);
    }

    public List<Comment> findByContent(String query) {
        if (query == null || query.isBlank()) {
            throw new BusinessRuleException("Invalid Query");
        }
        return dao.findByContent(query);
    }

    public List<Comment> findByUsername(String query) {
        if (query == null || query.isBlank()) {
            throw new BusinessRuleException("Invalid Query");
        }
        return dao.findByUsername(query);
    }
}
