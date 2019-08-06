package org.hopto.delow.chat.domain.exception;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists(String username) {
        super("User already exists: " + username);
    }
}
