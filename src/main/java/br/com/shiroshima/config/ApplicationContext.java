package br.com.shiroshima.config;

import br.com.shiroshima.controller.CommentController;
import br.com.shiroshima.controller.PostController;
import br.com.shiroshima.controller.UserController;
import br.com.shiroshima.repository.CommentDAO;
import br.com.shiroshima.repository.PostDAO;
import br.com.shiroshima.repository.UserDAO;
import br.com.shiroshima.service.CommentService;
import br.com.shiroshima.service.PostService;
import br.com.shiroshima.service.UserService;
import br.com.shiroshima.view.CommentView;
import br.com.shiroshima.view.MainView;
import br.com.shiroshima.view.PostView;
import br.com.shiroshima.view.UserView;

public class ApplicationContext {

    private static UserDAO userDAO;
    private static PostDAO postDAO;
    private static CommentDAO commentDAO;

    private static UserService userService;
    private static PostService postService;
    private static CommentService commentService;

    private static UserController userController;
    private static PostController postController;
    private static CommentController commentController;

    private static UserView userView;
    private static PostView postView;
    private static CommentView commentView;
    private static MainView mainView;

    private ApplicationContext() {
        throw new UnsupportedOperationException("ApplicationContext is a utility class");
    }

    // ==================== DAO ====================

    private static UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    private static PostDAO getPostDAO() {
        if (postDAO == null) {
            postDAO = new PostDAO();
        }
        return postDAO;
    }

    private static CommentDAO getCommentDAO() {
        if (commentDAO == null) {
            commentDAO = new CommentDAO();
        }
        return commentDAO;
    }

    // ==================== SERVICE ====================

    private static UserService getUserService() {
        if (userService == null) {
            userService = new UserService(getUserDAO());
        }
        return userService;
    }

    private static PostService getPostService() {
        if (postService == null) {
            postService = new PostService(getPostDAO());
        }
        return postService;
    }

    private static CommentService getCommentService() {
        if (commentService == null) {
            commentService = new CommentService(getCommentDAO(), getPostService());
        }
        return commentService;
    }

    // ==================== CONTROLLER ====================

    private static UserController getUserController() {
        if (userController == null) {
            userController = new UserController(getUserService());
        }
        return userController;
    }

    private static PostController getPostController() {
        if (postController == null) {
            postController = new PostController(getPostService());
        }
        return postController;
    }

    private static CommentController getCommentController() {
        if (commentController == null) {
            commentController = new CommentController(getCommentService());
        }
        return commentController;
    }

    // ==================== VIEW ====================

    private static UserView getUserView() {
        if (userView == null) {
            userView = new UserView(getUserController());
        }
        return userView;
    }

    private static CommentView getCommentView() {
        if (commentView == null) {
            commentView = new CommentView(getCommentController());
        }
        return commentView;
    }

    private static PostView getPostView() {
        if (postView == null) {
            postView = new PostView(getPostController(), getCommentView());
        }
        return postView;
    }

    public static MainView getMainView() {
        if (mainView == null) {
            mainView = new MainView(getUserView(), getPostView(), getCommentView());
        }
        return mainView;
    }
}

