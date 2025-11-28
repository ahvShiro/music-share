package br.com.shiroshima.service;

import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.*;
import br.com.shiroshima.repository.UserDAO;
import br.com.shiroshima.security.AuthContext;
import br.com.shiroshima.security.PasswordHasher;
import jakarta.persistence.EntityManager;

import java.util.InputMismatchException;
import java.util.Optional;

public class UserService {
    private final UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    // VALIDATIONS
    private void passwordValidation(String password) {
        if (password == null || password.isBlank()) {
            throw new BusinessRuleException("Password cannot be empty");
        }
        if (password.length() <= 5) {
            throw new BusinessRuleException("Password must have at least 5 characters");
        }
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }

        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial) {
            throw new BusinessRuleException("Password must have at least one lowercase and uppercase character, one digit, and one special character");
        }
    }
    private void usernameValidation(String username) {
        if (username == null || username.isBlank()) {
            throw new BusinessRuleException("Username cannot be empty");
        }
        if (searchByUsername(username) != null) {
            throw new BusinessRuleException("User already exists");
        }
        if (username.length() > 20) {
            throw new BusinessRuleException("Your username is too long (max 20 characters)");
        }
    }
    public boolean auth(String username, String password) {
        User user = searchByUsername(username);

        boolean isAuth = PasswordHasher.passwordMatchesHash(password, user.getPassword());

        if (isAuth) {
            AuthContext.login(user);
            return true;
        }
        return false;
    }

    // CRUD
    public User create(String username, String password, String bio) {
        try {

            usernameValidation(username);
            passwordValidation(password);

            String hashedPassword = PasswordHasher.hashPassword(password);
            return dao.save(new User(username, hashedPassword, bio));

        } catch (BusinessRuleException e) {
            throw e;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ServiceException("Failed to create user: " + e.getMessage());
        }
    }

    public User updatePassword(String passwordCheck, String newPassword) {
        try {
            User user = AuthContext.getCurrentUser();

            String hashedOldPassword = user.getPassword();

            if (!PasswordHasher.passwordMatchesHash(passwordCheck, hashedOldPassword)) {
                throw new BusinessRuleException("Current password doesn't match");
            }
            if (PasswordHasher.passwordMatchesHash(newPassword, hashedOldPassword)) {
                throw new BusinessRuleException("New password cannot be the same as the old password");
            }

            passwordValidation(newPassword);

            String hashedNewPassword = PasswordHasher.hashPassword(newPassword);
            user.setPassword(hashedNewPassword);

            dao.update(user);

            return user;

        } catch (RuntimeException e) {
            throw new ServiceException("Failed to update user password");
        }

    }

    public User update(String username, String bio) {
        try {
            User user = AuthContext.getCurrentUser();

            AuthService.assureUserIsOwner(user);
            usernameValidation(username);

            user.setUsername(username);
            user.setBio(bio);

            return dao.update(user);
        } catch (DAOException e) {
            throw new ServiceException("Failed to update user: " + e.getMessage());
        }
    }

    public User searchById(Long id) {
        Optional<User> user = dao.findById(id);
        return user.orElse(null);
    }

    public User searchByUsername(String username) {
        Optional<User> user = dao.findByUsername(username);
        return user.orElse(null);
    }
}
