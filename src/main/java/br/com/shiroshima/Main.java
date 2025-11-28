package br.com.shiroshima;


import br.com.shiroshima.controller.UserController;
import br.com.shiroshima.repository.UserDAO;
import br.com.shiroshima.service.UserService;
import br.com.shiroshima.view.UserView;

public class Main {

    public static void main(String[] args) {
        UserService service = new UserService(new UserDAO());
        UserController controller = new UserController(service);
        UserView view = new UserView(controller);

        view.runMenu();
    }
}