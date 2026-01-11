package io.ningelschlingel.pca.userauth.core.port.out;

public interface PasswordHasher {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String hashed);
}