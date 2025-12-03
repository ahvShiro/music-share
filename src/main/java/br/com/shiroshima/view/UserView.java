package br.com.shiroshima.view;

import br.com.shiroshima.controller.UserController;
import br.com.shiroshima.dto.ResultDTO;
import br.com.shiroshima.entity.User;

import java.util.Scanner;

public class UserView {

    private final Scanner scanner = new Scanner(System.in);
    private final UserController controller;

    public UserView(UserController controller) {
        this.controller = controller;
    }

    public void printUser(User user) {
        if (user == null) {
            System.out.println("No user to display.");
            return;
        }

        Long userId = user.getId();
        String username = user.getUsername();
        int postQt = user.getPosts().size();
        String postOrPosts = postQt <= 1 ? "Post" : "Posts";
        String bio = user.getBio();

        System.out.printf("== (%d) @%s ==%n", userId, username);
        System.out.printf("> %d %s%n", postQt, postOrPosts);

        if (bio != null && !bio.isEmpty()) {
            System.out.printf("> Bio: %s%n", bio);
        } else {
            System.out.println("> (no bio)");
        }
    }

    public void authUser() {
        MainView.printHeader("AUTH");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        ResultDTO<User> result = controller.authUser(username, password);
        if (result.isSuccess()) {
            System.out.println("Successfully authenticated!");
            printUser(result.getData());
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
            System.out.println("User created successfully!");
            printUser(result.getData());
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
            System.out.println("Profile updated successfully!");
            printUser(result.getData());
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
            System.out.println("Password updated successfully!");
            printUser(result.getData());
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
            System.out.println("User found!");
            printUser(result.getData());
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void runMenu() {
        boolean isExit = false;

        while (!isExit) {
            System.out.println();
            MainView.printHeader("User Menu");
            System.out.println("1 - Authenticate");
            System.out.println("2 - Create user");
            System.out.println("3 - Update profile");
            System.out.println("4 - Update password");
            System.out.println("5 - Search user");
            System.out.println("0 - Return");
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
                    isExit = true;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

}
