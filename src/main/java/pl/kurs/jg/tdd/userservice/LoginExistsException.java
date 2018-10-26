package pl.kurs.jg.tdd.userservice;

public class LoginExistsException extends RuntimeException {

    LoginExistsException(String login) {
        super("Login \"" + login + "\" already exists!");
    }
}
