package br.com.shiroshima;


import br.com.shiroshima.entity.User;
import br.com.shiroshima.repository.UserDAO;
import br.com.shiroshima.security.AuthContext;
import br.com.shiroshima.service.UserService;

public class Main {

    public static void main(String[] args) {
        UserService service = new UserService(new UserDAO());

        User user = service.create("Teste", "123ABCabc!@#", "Hello world");

        boolean isAuth = service.auth("Teste", "123ABCabc!@#");

        System.out.println(isAuth);
        System.out.println(AuthContext.getCurrentUser());
    }
}