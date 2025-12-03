package br.com.shiroshima.utils.security;

import br.com.shiroshima.entity.User;

public class AuthContext {
    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}
