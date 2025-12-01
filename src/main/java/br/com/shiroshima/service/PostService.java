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

            if (title != null && !title.trim().isEmpty()) {
                post.setTitle(title);
            }
            if (music != null) {
                post.setMusic(music);
            }
            if (description != null) {
                post.setDescription(description);
            }

            return dao.update(post);

        } catch (BusinessRuleException | EntityNotFoundException e) {
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
        Optional<Post> post = dao.findById(id);
        return post.orElse(null);
    }

    public List<Post> findByTitle(String title) {
        return dao.findByTitle(title);
    }

    public List<Post> findByOwner(User owner) {
        return dao.findByOwner(owner);
    }

    public List<Post> findOwn() {
        return dao.findOwn();
    }

    public List<Post> findAll() {
        return dao.findAll();
    }

}

