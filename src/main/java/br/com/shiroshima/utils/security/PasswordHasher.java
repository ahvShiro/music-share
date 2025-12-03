package br.com.shiroshima.utils.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String hashPassword(String password) {
        int logRounds = 10;
        String salt = BCrypt.gensalt(logRounds);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean passwordMatchesHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
