package br.com.shiroshima.controller;

import br.com.shiroshima.dto.ResultDTO;
import br.com.shiroshima.entity.User;
import br.com.shiroshima.service.UserService;

public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    public ResultDTO<User> createUser(String username, String password, String bio) {
        try {
            User user = service.create(username, password, bio);
            return ResultDTO.ok(user);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    public ResultDTO<User> updateUser(String username, String bio) {
        try {
            User user = service.update(username, bio);
            return ResultDTO.ok(user);
        } catch (Exception e) {
            return ResultDTO.fail("Error: " + e.getMessage());
        }
    }

    public ResultDTO<User> updatePassword(String passwordCheck, String newPassword) {
        try {
            User user = service.updatePassword(passwordCheck, newPassword);
            return ResultDTO.ok(user);
        } catch (Exception e) {
            return ResultDTO.fail("Error: " + e.getMessage());
        }
    }


    public ResultDTO<User> authUser(String username, String password) {
        try {
            User user = service.auth(username, password);
            return ResultDTO.ok(user);
        } catch (Exception e) {
            return ResultDTO.fail("Error: " + e.getMessage());
        }
    }

    public ResultDTO<User> searchByUsername(String username) {
        try {
            User user = service.searchByUsername(username);
            return ResultDTO.ok(user);
        } catch (Exception e) {
            return ResultDTO.fail("Error: " + e.getMessage());
        }
    }

}
