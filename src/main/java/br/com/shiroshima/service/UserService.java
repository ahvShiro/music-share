package br.com.shiroshima.service;

import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.*;
import br.com.shiroshima.repository.UserDAO;
import br.com.shiroshima.security.PasswordHasher;
import jakarta.persistence.EntityManager;

import java.util.InputMismatchException;
import java.util.Optional;

public class UserService {
    private final UserDAO dao;
    private final EntityManager em;

    public UserService(UserDAO dao, EntityManager em) {
        this.dao = dao;
        this.em = em;
    }

    private void passwordValidation(String password) {
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
            throw new BusinessRuleException("Password must have at least one lowercase and uppercase character, one digit and one special character");
        }
    }

    public User updatePassword(Long id, String passwordCheck, String newPassword) {
        try {
            em.getTransaction().begin();

            User user = searchById(id);
            String hashedOldPassword = user.getPassword();

            if (!PasswordHasher.passwordMatchesHash(passwordCheck, hashedOldPassword)) {
                throw new InputMismatchException("Current password doesn't match");
            }
            if (PasswordHasher.passwordMatchesHash(newPassword, hashedOldPassword)) {
                throw new InputMismatchException("New password cannot be the same as the old password");
            }

            passwordValidation(newPassword);

            String hashedNewPassword = PasswordHasher.hashPassword(newPassword);
            user.setPassword(hashedNewPassword);
            dao.update(user);

            em.getTransaction().commit();

            return user;

        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw new ServiceException("Failed to update user password");
        }

    }

    public User create(String username, String password, String bio) {
        try {
            em.getTransaction().begin();

            passwordValidation(password);

            String hashedPassword = PasswordHasher.hashPassword(password);
            User newUser = dao.save(new User(username, hashedPassword, bio));

            em.getTransaction().commit();

            return newUser;
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw new ServiceException("Failed to create user");
        }
    }

    public boolean auth(String username, String password) {
        User user = searchByUsername(username);
        return PasswordHasher.passwordMatchesHash(password, user.getPassword());
    }

//    public User update(Long id, String username, String bio) {
//        try {
//            em.getTransaction().begin();
//            User user = searchById(id);
//
//            User newUser = dao.save(new User(username, , bio));
//
//            em.getTransaction().commit();
//
//            return newUser;
//        } catch (DAOException e) {
//            em.getTransaction().rollback();
//            throw new RepositoryException("Failed to create user");
//        }
//    }

    public User searchById(Long id) {
        Optional<User> user = dao.findById(id);
        if (user.isEmpty()) throw new EntityNotFoundException("Entity not found with id: " + id);
        return user.get();
    }

    public User searchByUsername(String username) {
        Optional<User> user = dao.findByUsername(username);
        if (user.isEmpty()) throw new EntityNotFoundException("Entity not found with username: " + username);
        return user.get();
    }
}
