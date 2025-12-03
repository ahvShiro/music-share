package br.com.shiroshima.view;

import br.com.shiroshima.utils.security.AuthContext;

import java.util.Scanner;

public class MainView {

    private final Scanner scanner = new Scanner(System.in);
    private final UserView userView;
    private final PostView postView;
    private final CommentView commentView;

    public MainView(UserView userView, PostView postView, CommentView commentView) {
        this.userView = userView;
        this.postView = postView;
        this.commentView = commentView;
    }

    static void printHeader(String title) {
        System.out.println("== " + title + " ==");
    }

    public void runMenu() {
        boolean isExit = false;

        while (!isExit) {
            if (AuthContext.getCurrentUser() == null) {

                System.out.println();
                printHeader("Main Menu");
                System.out.println("1 - Authenticate");
                System.out.println("2 - Create user");

                System.out.println("0 - Return");
                System.out.print("> ");

                String option = scanner.nextLine().trim();
                switch (option) {
                    case "1" -> userView.authUser();
                    case "2" -> userView.createUser();
                    case "0" -> {
                        System.out.println("Exiting user menu.");
                        isExit = true;
                    }
                    default -> System.out.println("Invalid option, try again.");
                }

            } else {

                System.out.println();
                printHeader("Main Menu");
                System.out.println("1 - User settings");
                System.out.println("2 - Post menu");
                System.out.println("3 - Comment menu");
                System.out.println("0 - Return");
                System.out.print("> ");

                String option = scanner.nextLine().trim();
                switch (option) {
                    case "1" -> userView.runMenu();
                    case "2" -> postView.runMenu();
                    case "3" -> commentView.runMenu();
                    case "0" -> {
                        System.out.println("Exiting main menu.");
                        isExit = true;
                    }
                    default -> System.out.println("Invalid option, try again.");

                }
            }
        }
    }
}
