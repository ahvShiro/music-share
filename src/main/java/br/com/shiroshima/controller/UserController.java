package br.com.shiroshima.controller;

import br.com.shiroshima.entity.User;
import br.com.shiroshima.service.UserService;

import java.util.Scanner;

public class UserController {
    private final UserService userService;
    private final Scanner scanner;
    private User loggedUser;

    public UserController(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
        this.loggedUser = null;
    }

    public void start() {
        menuInicial();
    }

    private void menuInicial() {
        System.out.println("== MENU ==");
        System.out.println("1. Login");
        System.out.println("2. Criar conta");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> login();
                case 2 -> criarConta();
                case 3 -> {
                    System.out.println("Até logo!");
                    scanner.close();
                }
                default -> {
                    System.out.println("\nOpção inválida!");
                    menuInicial();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("\nPor favor, digite um número válido!");
            menuInicial();
        }
    }

    private void login() {
        System.out.println("\n== LOGIN ==");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        try {
            boolean authenticated = userService.auth(username, password);

            if (authenticated) {
                loggedUser = userService.searchByUsername(username);
                System.out.println("\nLogin realizado com sucesso!");
                System.out.println("Bem-vindo, " + loggedUser.getUsername() + "!");

                proximaTela();
            } else {
                System.out.println("\nUsuário ou senha incorretos!");
                menuInicial();
            }
        } catch (Exception e) {
            System.out.println("\nErro ao fazer login: " + e.getMessage());
            menuInicial();
        }
    }

    private void criarConta() {
        System.out.println("\n== CRIAR CONTA ==");

        System.out.print("Username (máx. 20 caracteres): ");
        String username = scanner.nextLine();

        System.out.println("\nA senha deve conter:");
        System.out.println("- Mínimo 6 caracteres");
        System.out.println("- Letras maiúsculas e minúsculas");
        System.out.println("- Números");
        System.out.println("- Caracteres especiais");

        System.out.print("\nSenha: ");
        String password = scanner.nextLine();

        System.out.print("Biografia (opcional): ");
        String bio = scanner.nextLine();

        try {
            User newUser = userService.create(username, password, bio);

            if (newUser != null) {
                System.out.println("\nConta criada com sucesso!");
                System.out.println("Você já pode fazer login.");
                menuInicial();
            } else {
                System.out.println("\nNome de usuário já existe!");
                menuInicial();
            }
        } catch (Exception e) {
            System.out.println("\nErro ao criar conta: " + e.getMessage());
            System.out.println("Tente novamente.");
            menuInicial();
        }
    }

    private void proximaTela() {

    }
}
