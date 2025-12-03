package br.com.shiroshima.controller;

import br.com.shiroshima.entity.Post;
import br.com.shiroshima.dto.ResultDTO;
import br.com.shiroshima.service.PostService;

import java.util.List;

public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public ResultDTO<Post> createPost(String title, String music, String description) {
        try {
            Post newPost = service.create(title, music, description);
            return ResultDTO.ok(newPost);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<Post> updatePost(Long postId, String title, String music, String description) {
        try {
            Post updatedPost = service.update(postId, title, music, description);
            return ResultDTO.ok(updatedPost);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<Post> deletePost(Long postId) {
        try {
            service.delete(postId);
            return ResultDTO.ok(null);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }


    public ResultDTO<Post> findPostById(Long id) {
        try {
            Post post = service.findById(id);
            return ResultDTO.ok(post);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    // paginação é para os fracos B) (não queria implementar na mão)
    public ResultDTO<List<Post>> findPostsByTitle(String title) {
        try {
            List<Post> posts = service.findByTitle(title);
            return ResultDTO.ok(posts);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<List<Post>> findPostsByOwnerId(Long id) {
        try {
            List<Post> posts = service.findByOwnerId(id);
            return ResultDTO.ok(posts);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<List<Post>> findPostsByOwnerUsername(String username) {
        try {
            List<Post> posts = service.findByOwnerUsername(username);
            return ResultDTO.ok(posts);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<List<Post>> findOwnPosts() {
        try {
            List<Post> posts = service.findOwn();
            return ResultDTO.ok(posts);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<List<Post>> findAllPosts() {
        try {
            List<Post> posts = service.findAll();
            return ResultDTO.ok(posts);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }
}


