package br.com.shiroshima.view;

import br.com.shiroshima.controller.CommentController;
import br.com.shiroshima.entity.Comment;
import br.com.shiroshima.dto.ResultDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CommentView {

    private final Scanner scanner = new Scanner(System.in);
    private final CommentController controller;

    public CommentView(CommentController controller) {
        this.controller = controller;
    }

    public void printComment(Comment comment) {
        if (comment == null) {
            System.out.println("No comment to display.");
            return;
        }

        Long commentId = comment.getId();
        String username = comment.getUser() != null ? comment.getUser().getUsername() : "Unknown";
        String content = comment.getContent();
        String createdAt = comment.getCreatedAt() != null ? comment.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "Unknown";

        System.out.println();
        System.out.printf("== Comment #%d ==%n", commentId);
        System.out.printf("> @%s - %s%n", username, createdAt);

        if (content != null && !content.isEmpty()) {
            System.out.printf("> %s%n", content);
        } else {
            System.out.println("> (no content)");
        }
    }

    public void createComment() {
        MainView.printHeader("CREATE COMMENT");
        System.out.print("Post ID: ");
        String postIdStr = scanner.nextLine().trim();
        try {
            Long postId = Long.parseLong(postIdStr);

            System.out.print("Comment: ");
            String content = scanner.nextLine().trim();

            ResultDTO<Comment> result = controller.createComment(content, postId);
            if (result.isSuccess()) {
                System.out.println("Comment created successfully!");
                printComment(result.getData());
            } else {
                System.out.println(result.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID.");
        }
    }

    public void deleteComment() {
        MainView.printHeader("DELETE COMMENT");
        System.out.print("Comment ID: ");
        String idStr = scanner.nextLine().trim();
        try {
            Long commentId = Long.parseLong(idStr);
            ResultDTO<Comment> result = controller.deleteComment(commentId);
            if (result.isSuccess()) {
                System.out.println("Comment deleted successfully.");
            } else {
                System.out.println(result.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid comment ID.");
        }
    }

    public void findCommentById() {
        MainView.printHeader("FIND COMMENT BY ID");
        System.out.print("Comment ID: ");
        String idStr = scanner.nextLine().trim();
        try {
            Long commentId = Long.parseLong(idStr);
            ResultDTO<Comment> result = controller.findById(commentId);
            if (result.isSuccess()) {
                System.out.println("Comment found!");
                printComment(result.getData());
            } else {
                System.out.println(result.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid comment ID.");
        }
    }

    public void findAllComments() {
        MainView.printHeader("ALL COMMENTS");
        ResultDTO<List<Comment>> result = controller.findAll();
        if (result.isSuccess()) {
            List<Comment> comments = result.getData();
            if (comments.isEmpty()) {
                System.out.println("No comments found.");
            } else {
                System.out.println("Found " + comments.size() + " comment(s):");
                comments.forEach(this::printComment);
            }
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void findCommentsByPostId() {
        MainView.printHeader("FIND COMMENTS BY POST ID");
        System.out.print("Post ID: ");
        String idStr = scanner.nextLine().trim();
        try {
            Long postId = Long.parseLong(idStr);
            ResultDTO<List<Comment>> result = controller.findByPostId(postId);

            if (result.isSuccess()) {
                List<Comment> comments = result.getData();

                if (comments.isEmpty()) {
                    System.out.println("No comments found for this post.");
                } else {
                    System.out.println("Found " + comments.size() + " comment(s):");
                    comments.forEach(this::printComment);
                }

            } else {
                System.out.println(result.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID.");
        }
    }

    public void findCommentsByContent() {
        MainView.printHeader("FIND COMMENTS BY CONTENT");
        System.out.print("Content: ");
        String content = scanner.nextLine().trim();

        try {
            ResultDTO<List<Comment>> result = controller.findByContent(content);

            if (result.isSuccess()) {
                List<Comment> comments = result.getData();

                if (comments.isEmpty()) {
                    System.out.println("No comments found with: " + content);

                } else {
                    System.out.println("Found " + comments.size() + " comment(s):");
                    comments.forEach(this::printComment);
                }
            } else {
                System.out.println(result.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public void findCommentsByUsername() {
        MainView.printHeader("FIND COMMENTS BY USERNAME");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        try {
            ResultDTO<List<Comment>> result = controller.findByUsername(username);

            if (result.isSuccess()) {
                List<Comment> comments = result.getData();

                if (comments.isEmpty()) {
                    System.out.println("No comments found with username: " + username);
                } else {
                    System.out.println("Found " + comments.size() + " comment(s):");
                    comments.forEach(this::printComment);
                }
            } else {
                System.out.println(result.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public void runMenu() {
        boolean isExit = false;

        while (!isExit) {
            System.out.println();
            MainView.printHeader("Comment Menu");

            System.out.println("1 - Create comment          4 - Find comment by content");
            System.out.println("2 - Delete comment          5 - Find comment by username");
            System.out.println("3 - Find comments by post");

            System.out.println("0 - Return");
            System.out.print("> ");

            String option = scanner.nextLine().trim();
            switch (option) {
                case "1" -> createComment();
                case "2" -> deleteComment();
                case "3" -> findCommentsByPostId();
                case "4" -> findCommentsByContent();
                case "5" -> findCommentsByUsername();
                case "0" -> {
                    System.out.println("Exiting post menu.");
                    isExit = true;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

}
