package br.com.shiroshima.service;


import br.com.shiroshima.entity.User;
import br.com.shiroshima.exception.BusinessRuleException;
import br.com.shiroshima.utils.security.AuthContext;

public class AuthService {

    public static void assureUserIsOwner(User owner) {
        User current = AuthContext.getCurrentUser();

        if (current == null) {
            throw new BusinessRuleException("You must be logged in");
        }

        if (!current.getId().equals(owner.getId())) {
            throw new BusinessRuleException("You are not authorized to do this action");
        }
    }
}
