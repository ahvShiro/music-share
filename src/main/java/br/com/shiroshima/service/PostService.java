package br.com.shiroshima.service;

import br.com.shiroshima.entity.Post;
import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.*;
import br.com.shiroshima.repository.PostDAO;
import br.com.shiroshima.security.AuthContext;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostDAO dao;

    public PostService(PostDAO dao) {
        this.dao = dao;
    }

    private void validateOwner(Post post) {
        if (post.getOwner() != AuthContext.getCurrentUser() || AuthContext.getCurrentUser() == null) {
            throw new BusinessRuleException("You do not have permission to do this action");
        }
    }

    public Post create(String title, String music, String description) {
        try {

            if (title == null || title.isBlank()) {
                throw new BusinessRuleException("Title cannot be empty");
            }
            if (music == null || music.isBlank()) {
                throw new BusinessRuleException("Music cannot be empty");
            }

            Post newPost = new Post(title, music, description, AuthContext.getCurrentUser());
            return dao.save(newPost);

        } catch (BusinessRuleException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException("Failed to create post: " + e.getMessage());
        }
    }

    public Post update(Long postId, String title, String music, String description) {
        try {
            Post post = findById(postId);

            validateOwner(post);

            if (title == null || title.trim().isEmpty()) {
                throw new BusinessRuleException("Title cannot be empty");
            }
            if (music == null) {
                throw new BusinessRuleException("Music cannot be empty");
            }
            if (description == null) {
                throw new BusinessRuleException("Description cannot be null");
            }
            post.setTitle(title);
            post.setMusic(music);
            post.setDescription(description);

            return dao.update(post);

        } catch (BusinessRuleException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException("Failed to update post: " + e.getMessage());
        }
    }

    public void delete(Long postId) {
        try {
            Post post = findById(postId);

            validateOwner(post);

            dao.delete(post);

        } catch (BusinessRuleException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ServiceException("Failed to delete post: " + e.getMessage());
        }
    }

    public Post findById(Long id) {
        if (id == null || id <= (long) 0) {
            throw new BusinessRuleException("Invalid Id");
        }
        Optional<Post> post = dao.findById(id);
        return post.orElse(null);
    }

    public List<Post> findByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BusinessRuleException("Invalid Title");
        }
        return dao.findByTitle(title);
    }

    // só pra caso seja necessário (talvez nunca)
    public List<Post> findByOwner(User owner) {
        if (owner == null) {
            throw new BusinessRuleException("Invalid User");
        }
        return dao.findByOwner(owner);
    }

    public List<Post> findByOwnerId(Long userId) {
        if (userId == null || userId <= (long) 0) {
            throw new BusinessRuleException("Invalid User Id");
        }
        return dao.findByOwnerId(userId);
    }

    public List<Post> findByOwnerUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new BusinessRuleException("Invalid Username");
        }
        return dao.findByOwnerUsermane(username);
    }

    public List<Post> findOwn() {
        return dao.findOwn();
    }

    public List<Post> findAll() {
        return dao.findAll();
    }

}

