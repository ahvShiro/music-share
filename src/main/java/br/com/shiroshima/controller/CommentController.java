package br.com.shiroshima.controller;

import br.com.shiroshima.entity.Comment;
import br.com.shiroshima.dto.ResultDTO;
import br.com.shiroshima.service.CommentService;

import java.util.List;

public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    public ResultDTO<Comment> createComment(String content, Long postId) {
        try {
            Comment comment = service.create(content, postId);
            return ResultDTO.ok(comment);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<Comment> deleteComment(Long commentId)  {
        try {
            service.delete(commentId);
            return ResultDTO.ok(null);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<Comment> findById(Long id) {
        try {
            Comment comment = service.findById(id);
            return ResultDTO.ok(comment);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<List<Comment>> findAll() {
        try {
            List<Comment> comments = service.findAll();
            return ResultDTO.ok(comments);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<List<Comment>> findByPostId(Long postId) {
        try {
            List<Comment> comments = service.findByPostId(postId);
            return ResultDTO.ok(comments);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<List<Comment>> findByContent(String content) {
        try {
            List<Comment> comments = service.findByContent(content);
            return ResultDTO.ok(comments);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<List<Comment>> findByUsername(String username) {
        try {
            List<Comment> comments = service.findByUsername(username);
            return ResultDTO.ok(comments);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

}
