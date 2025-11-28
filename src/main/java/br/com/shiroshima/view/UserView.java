package br.com.shiroshima.view;

import br.com.shiroshima.controller.UserController;
import br.com.shiroshima.entity.ResultDTO;
import br.com.shiroshima.entity.User;

import java.util.Scanner;

public class UserView {

    private UserController controller;
    private final Scanner scanner = new Scanner(System.in);

    public UserView(UserController controller) {
        this.controller = controller;
    }

    public void authUser() {
        MainView.printHeader("AUTH");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        ResultDTO<User> result = controller.authUser(username, password);
        if (result.isSuccess()) {
            System.out.println("Successfully authenticated: " + result.getData());
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void createUser() {
        MainView.printHeader("CREATE USER");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Bio (optional): ");
        String bio = scanner.nextLine().trim();

        ResultDTO<User> result = controller.createUser(username, password, bio);
        if (result.isSuccess()) {
            System.out.println("User created: " + result.getData());
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void updateUser() {
        MainView.printHeader("UPDATE PROFILE");
        System.out.print("New username: ");
        String username = scanner.nextLine().trim();
        System.out.print("New bio: ");
        String bio = scanner.nextLine().trim();

        ResultDTO<User> result = controller.updateUser(username, bio);
        if (result.isSuccess()) {
            System.out.println("Profile updated: " + result.getData());
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void updatePassword() {
        MainView.printHeader("UPDATE PASSWORD");
        System.out.print("Current password: ");
        String current = scanner.nextLine().trim();
        System.out.print("New password: ");
        String neu = scanner.nextLine().trim();

        ResultDTO<User> result = controller.updatePassword(current, neu);
        if (result.isSuccess()) {
            System.out.println("Password updated for user: " + result.getData());
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void searchByUsername() {
        MainView.printHeader("SEARCH USER");
        System.out.print("Username to search: ");
        String username = scanner.nextLine().trim();

        ResultDTO<User> result = controller.searchByUsername(username);
        if (result.isSuccess()) {
            System.out.println("User found: " + result.getData());
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void runMenu() {
        while (true) {
            System.out.println();
            System.out.println("=== User Menu ===");
            System.out.println("1 - Authenticate");
            System.out.println("2 - Create user");
            System.out.println("3 - Update profile");
            System.out.println("4 - Update password");
            System.out.println("5 - Search user");
            System.out.println("0 - Exit");
            System.out.print("> ");

            String option = scanner.nextLine().trim();
            switch (option) {
                case "1" -> authUser();
                case "2" -> createUser();
                case "3" -> updateUser();
                case "4" -> updatePassword();
                case "5" -> searchByUsername();
                case "0" -> {
                    System.out.println("Exiting user menu.");
                    return;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

}
