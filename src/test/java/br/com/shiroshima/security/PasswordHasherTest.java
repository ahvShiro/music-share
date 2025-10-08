package br.com.shiroshima.security;

import org.junit.jupiter.api.*;

public class PasswordHasherTest {

    @Test
    public void givenPassword_whenHashPassword_thenReturnHashedPassword() {
        String password = "S3nH4#f0rt3$123";
        String hash = PasswordHasher.hashPassword(password);
        Assertions.assertTrue(PasswordHasher.passwordMatchesHash(password, hash));
    }

}
