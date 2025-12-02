package br.com.shiroshima.view;

import br.com.shiroshima.controller.PostController;
import br.com.shiroshima.entity.Post;
import br.com.shiroshima.entity.ResultDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class PostView {

    private final Scanner scanner = new Scanner(System.in);
    private final PostController controller;

    public PostView(PostController controller) {
        this.controller = controller;
    }

    public void printPost(Post post) {
        if (post == null) {
            System.out.println("No post to display.");
            return;
        }

        Long postId = post.getId();
        String title = post.getTitle();
        String ownerUsername = post.getOwner() != null ? post.getOwner().getUsername() : "Unknown";
        String music = post.getMusic();
        String description = post.getDescription();
        String createdAt = post.getCreatedAt() != null ? post.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Unknown";

        System.out.println();

        System.out.printf("== (%d) %s ==%n", postId, title);
        System.out.printf("> @%s - %s%n", ownerUsername, createdAt);

        if (description != null && !description.isEmpty()) {
            System.out.printf("> Description: %s%n", description);
        } else {
            System.out.println("> (no description)");
        }
        System.out.printf("> %s%n", music);

    }

    public void createPost() {
        MainView.printHeader("CREATE POST");
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Music: ");
        String music = scanner.nextLine().trim();
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        ResultDTO<Post> result = controller.createPost(title, music, description);
        if (result.isSuccess()) {
            System.out.println("Post created successfully!");
            printPost(result.getData());
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void updatePost() {
        MainView.printHeader("UPDATE POST");
        System.out.print("Post ID: ");
        String idStr = scanner.nextLine().trim();
        try {
            Long postId = Long.parseLong(idStr);
            System.out.print("New title: ");
            String title = scanner.nextLine().trim();
            System.out.print("New music: ");
            String music = scanner.nextLine().trim();
            System.out.print("New description: ");
            String description = scanner.nextLine().trim();

            ResultDTO<Post> result = controller.updatePost(postId, title, music, description);
            if (result.isSuccess()) {
                System.out.println("Post updated successfully!");
                printPost(result.getData());
            } else {
                System.out.println(result.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID.");
        }
    }

    public void deletePost() {
        MainView.printHeader("DELETE POST");
        System.out.print("Post ID: ");
        String idStr = scanner.nextLine().trim();
        try {
            Long postId = Long.parseLong(idStr);
            ResultDTO<Post> result = controller.deletePost(postId);
            if (result.isSuccess()) {
                System.out.println("Post deleted successfully.");
            } else {
                System.out.println(result.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID.");
        }
    }

    public void findPostById() {
        MainView.printHeader("FIND POST BY ID");
        System.out.print("Post ID: ");
        String idStr = scanner.nextLine().trim();
        try {
            Long postId = Long.parseLong(idStr);
            ResultDTO<Post> result = controller.findPostById(postId);
            if (result.isSuccess()) {
                System.out.println("Post found!");
                printPost(result.getData());
            } else {
                System.out.println(result.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID.");
        }
    }

    public void findPostsByTitle() {
        MainView.printHeader("FIND POSTS BY TITLE");
        System.out.print("Title to search: ");
        String title = scanner.nextLine().trim();

        ResultDTO<List<Post>> result = controller.findPostsByTitle(title);
        if (result.isSuccess()) {
            List<Post> posts = result.getData();
            if (posts.isEmpty()) {
                System.out.println("No posts found with that title.");
            } else {
                System.out.println("Found " + posts.size() + " post(s):");
                posts.forEach(this::printPost);
            }
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void findPostsByOwnerId() {
        MainView.printHeader("FIND POSTS BY OWNER ID");
        System.out.print("Owner ID: ");
        String idStr = scanner.nextLine().trim();
        try {
            Long ownerId = Long.parseLong(idStr);
            ResultDTO<List<Post>> result = controller.findPostsByOwnerId(ownerId);
            if (result.isSuccess()) {
                List<Post> posts = result.getData();
                if (posts.isEmpty()) {
                    System.out.println("No posts found for this owner.");
                } else {
                    System.out.println("Found " + posts.size() + " post(s):");
                    posts.forEach(this::printPost);
                }
            } else {
                System.out.println(result.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid owner ID.");
        }
    }

    public void findPostsByOwnerUsername() {
        MainView.printHeader("FIND POSTS BY OWNER USERNAME");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        ResultDTO<List<Post>> result = controller.findPostsByOwnerUsername(username);
        if (result.isSuccess()) {
            List<Post> posts = result.getData();
            if (posts.isEmpty()) {
                System.out.println("No posts found for this user.");
            } else {
                System.out.println("Found " + posts.size() + " post(s):");
                posts.forEach(this::printPost);
            }
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void findOwnPosts() {
        MainView.printHeader("MY POSTS");
        ResultDTO<List<Post>> result = controller.findOwnPosts();
        if (result.isSuccess()) {
            List<Post> posts = result.getData();
            if (posts.isEmpty()) {
                System.out.println("You don't have any posts yet.");
            } else {
                System.out.println("Your " + posts.size() + " post(s):");
                posts.forEach(this::printPost);
            }
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void findAllPosts() {
        MainView.printHeader("ALL POSTS");
        ResultDTO<List<Post>> result = controller.findAllPosts();
        if (result.isSuccess()) {
            List<Post> posts = result.getData();
            if (posts.isEmpty()) {
                System.out.println("No posts available.");
            } else {
                System.out.println("Total of " + posts.size() + " post(s):");
                posts.forEach(this::printPost);
            }
        } else {
            System.out.println(result.getMessage());
        }
    }

    public void runMenu() {
        boolean isExit = false;

        while (!isExit) {
            System.out.println();
            MainView.printHeader("Post Menu");
            System.out.println("1 - Create post");
            System.out.println("2 - Update post");
            System.out.println("3 - Delete post");
            System.out.println("4 - Find post by ID");
            System.out.println("5 - Find posts by title");
            System.out.println("6 - Find posts by owner ID");
            System.out.println("7 - Find posts by owner username");
            System.out.println("8 - Find my posts");
            System.out.println("9 - Find all posts");
            System.out.println("0 - Return");
            System.out.print("> ");

            String option = scanner.nextLine().trim();
            switch (option) {
                case "1" -> createPost();
                case "2" -> updatePost();
                case "3" -> deletePost();
                case "4" -> findPostById();
                case "5" -> findPostsByTitle();
                case "6" -> findPostsByOwnerId();
                case "7" -> findPostsByOwnerUsername();
                case "8" -> findOwnPosts();
                case "9" -> findAllPosts();
                case "0" -> {
                    System.out.println("Exiting post menu.");
                    isExit = true;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

}
