package br.com.shiroshima;


import br.com.shiroshima.controller.PostController;
import br.com.shiroshima.controller.UserController;
import br.com.shiroshima.repository.PostDAO;
import br.com.shiroshima.repository.UserDAO;
import br.com.shiroshima.service.PostService;
import br.com.shiroshima.service.UserService;
import br.com.shiroshima.view.MainView;
import br.com.shiroshima.view.PostView;
import br.com.shiroshima.view.UserView;

public class Main {

    public static void main(String[] args) {
        UserService service = new UserService(new UserDAO());
        UserController controller = new UserController(service);
        UserView userView = new UserView(controller);

        PostService postService = new PostService(new PostDAO());
        PostController postController = new PostController(postService);
        PostView postView = new PostView(postController);

        MainView view = new MainView(userView, postView);

        view.runMenu();
    }
}